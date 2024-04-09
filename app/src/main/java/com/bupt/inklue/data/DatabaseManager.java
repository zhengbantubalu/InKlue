package com.bupt.inklue.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;

import com.bupt.inklue.R;
import com.bupt.inklue.util.BitmapProcessor;

import java.util.ArrayList;
import java.util.Arrays;

//数据库管理器
public class DatabaseManager {

    //初始化数据库
    public static void initDatabase(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS StdChar " +
                "(id INTEGER PRIMARY KEY, name TEXT, stdImgPath TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS WrittenChar " +
                "(id INTEGER PRIMARY KEY, name TEXT, stdImgPath TEXT, writtenImgPath TEXT, " +
                "score TEXT, advice TEXT)");
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
        FileManager.clearDir(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                "/record/char");
        FileManager.clearDir(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
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
    public static void putInitialData(SQLiteDatabase db, String dirPath) {
        ArrayList<CharData> charsData = new ArrayList<>();
        ArrayList<String> chars = new ArrayList<>(Arrays.asList(
                "土", "王", "五", "上", "下", "不", "之", "山", "廿", "四", "日", "石", "六", "天"));
        for (String c : chars) {
            String stdImgPath = dirPath + "/char/" + c + ".jpg";
            CharData charData = new CharData();
            charData.setStdImgPath(stdImgPath);
            charsData.add(charData);
            ContentValues values = new ContentValues();
            values.put("name", c);
            values.put("stdImgPath", stdImgPath);
            db.insert("StdChar", null, values);
        }
        ArrayList<String> names = new ArrayList<>(Arrays.asList(
                "峄山碑·壹", "峄山碑·贰", "峄山碑·叁"));
        ArrayList<String> charIDs = new ArrayList<>(Arrays.asList(
                "1,2,3,4,5", "6,7,8,9", "10,11,12,13,14"));
        ArrayList<ArrayList<CharData>> charsDataList = new ArrayList<>();
        charsDataList.add(new ArrayList<>(charsData.subList(0, 5)));
        charsDataList.add(new ArrayList<>(charsData.subList(5, 9)));
        charsDataList.add(new ArrayList<>(charsData.subList(9, 14)));
        for (int i = 0; i < 3; i++) {
            ContentValues values = new ContentValues();
            String name = names.get(i);
            String coverImgPath = dirPath + "/cover/" + name + ".jpg";
            PracticeData practiceData = new PracticeData();
            practiceData.charsData = charsDataList.get(i);
            BitmapProcessor.createCover(practiceData, coverImgPath, true);
            values.put("name", name);
            values.put("coverImgPath", coverImgPath);
            values.put("charIDs", charIDs.get(i));
            db.insert("Practice", null, values);
        }
    }
}
