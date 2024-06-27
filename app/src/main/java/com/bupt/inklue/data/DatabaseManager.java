package com.bupt.inklue.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;

import com.bupt.inklue.R;
import com.bupt.inklue.util.BitmapProcessor;

import java.util.ArrayList;

//数据库管理器
public class DatabaseManager {

    //初始化数据库
    public static void initDatabase(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS StdChar " +
                "(id INTEGER PRIMARY KEY, name TEXT, className TEXT, stdImgPath TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS WrittenChar " +
                "(id INTEGER PRIMARY KEY, name TEXT, className TEXT, stdImgPath TEXT, " +
                "writtenImgPath TEXT, score TEXT, advice TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Practice " +
                "(id INTEGER PRIMARY KEY, name TEXT, coverImgPath TEXT, charIDs TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Record " +
                "(id INTEGER PRIMARY KEY, name TEXT, time TEXT, coverImgPath TEXT, charIDs TEXT)");
    }

    //重置数据库
    public static void resetDatabase(Context context) {
        //删除数据库
        context.deleteDatabase("database.db");
        //清空记录目录
        FileManager.clearDirectory(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                "/record/char");
        FileManager.clearDirectory(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                "/record/cover");
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //初始化数据库
            initDatabase(db);
            //置入初始数据
            putInitialData(db, context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                    "/practice");
            db.close();
        }
        Toast.makeText(context, R.string.database_reset, Toast.LENGTH_SHORT).show();
    }

    //置入初始数据
    private static void putInitialData(SQLiteDatabase db, String dirPath) {
        putCharData(db, dirPath);//置入汉字数据
        putPracticeData(db, dirPath);//置入练习数据
    }

    //置入汉字数据
    private static void putCharData(SQLiteDatabase db, String dirPath) {
        ArrayList<String> cnChars = InitialData.getCnChars();
        ArrayList<String> classNames = InitialData.getClassNames();
        for (int i = 0; i < cnChars.size(); i++) {
            String cnChar = cnChars.get(i);
            String className = classNames.get(i);
            String stdImgPath = dirPath + "/char/" + className + ".jpg";
            CharData charData = new CharData();
            charData.setStdImgPath(stdImgPath);
            ContentValues values = new ContentValues();
            values.put("name", cnChar);
            values.put("className", className);
            values.put("stdImgPath", stdImgPath);
            db.insert("StdChar", null, values);
        }
    }

    //置入练习数据
    private static void putPracticeData(SQLiteDatabase db, String dirPath) {
        ArrayList<String> practiceNames = InitialData.getPracticeNames();
        ArrayList<String> practiceCharIDs = InitialData.getPracticeCharIDs();
        ArrayList<String> classNames = InitialData.getClassNames();
        for (int i = 0; i < practiceNames.size(); i++) {
            ContentValues values = new ContentValues();
            String name = practiceNames.get(i);
            String coverImgPath = dirPath + "/cover/" + name + ".jpg";
            PracticeData practiceData = new PracticeData();
            practiceData.charsData = new ArrayList<>();
            String[] idArray = practiceCharIDs.get(i).split(",");
            for (String id : idArray) {
                CharData charData = new CharData();
                String className = classNames.get(Integer.parseInt(id) - 1);
                String stdImgPath = dirPath + "/char/" + className + ".jpg";
                charData.setStdImgPath(stdImgPath);
                practiceData.charsData.add(charData);
            }
            BitmapProcessor.createCover(practiceData, coverImgPath, true);
            values.put("name", name);
            values.put("coverImgPath", coverImgPath);
            values.put("charIDs", practiceCharIDs.get(i));
            db.insert("Practice", null, values);
        }
    }
}
