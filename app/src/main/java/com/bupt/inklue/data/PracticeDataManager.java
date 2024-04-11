package com.bupt.inklue.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.widget.Toast;

import com.bupt.inklue.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

//练习数据管理器
public class PracticeDataManager {

    //取得标准汉字数据
    public static ArrayList<CharData> getStdCharsData(Context context, PracticeData practiceData) {
        ArrayList<CharData> charsData = new ArrayList<>();
        String[] idArray = practiceData.getCharIDs().split(",");
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (String id : idArray) {
                Cursor cursor = db.rawQuery("SELECT * FROM StdChar WHERE id = " +
                        id, null);
                int nameIndex = cursor.getColumnIndex("name");
                int stdImgPathIndex = cursor.getColumnIndex("stdImgPath");
                if (cursor.moveToFirst()) {
                    do {
                        CharData charData = new CharData();
                        charData.setID(Long.parseLong(id));
                        charData.setName(cursor.getString(nameIndex));
                        charData.setStdImgPath(cursor.getString(stdImgPathIndex));
                        charsData.add(charData);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
            db.close();
        }
        return charsData;
    }

    //取得书写汉字数据
    public static ArrayList<CharData> getWrittenCharsData(Context context, PracticeData practiceData) {
        ArrayList<CharData> charsData = new ArrayList<>();
        String[] idArray = practiceData.getCharIDs().split(",");
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (String id : idArray) {
                Cursor cursor = db.rawQuery("SELECT * FROM WrittenChar WHERE id = " +
                        id, null);
                int nameIndex = cursor.getColumnIndex("name");
                int stdImgPathIndex = cursor.getColumnIndex("stdImgPath");
                int writtenImgPathIndex = cursor.getColumnIndex("writtenImgPath");
                int scoreIndex = cursor.getColumnIndex("score");
                int adviceIndex = cursor.getColumnIndex("advice");
                if (cursor.moveToFirst()) {
                    do {
                        CharData charData = new CharData();
                        charData.setID(Long.parseLong(id));
                        charData.setName(cursor.getString(nameIndex));
                        charData.setStdImgPath(cursor.getString(stdImgPathIndex));
                        charData.setWrittenImgPath(cursor.getString(writtenImgPathIndex));
                        charData.setScore(cursor.getString(scoreIndex));
                        charData.setAdvice(cursor.getString(adviceIndex));
                        charsData.add(charData);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
            db.close();
        }
        return charsData;
    }

    //保存练习记录
    public static void saveRecord(Context context, PracticeData practiceData) {
        boolean fileSuccess = FileManager.moveRecordImg(context, practiceData);//移动记录图片存储位置
        int successNum = 0;
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            StringBuilder CharIDs = new StringBuilder();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //保存汉字
            for (CharData charData : practiceData.charsData) {
                ContentValues values = new ContentValues();
                values.put("name", charData.getName());
                values.put("stdImgPath", charData.getStdImgPath());
                values.put("writtenImgPath", charData.getWrittenImgPath());
                values.put("score", charData.getScore());
                values.put("advice", charData.getAdvice());
                long newID = db.insert("WrittenChar", null, values);
                if (newID != -1) {
                    successNum++;
                    CharIDs.append(newID).append(",");
                }
            }
            CharIDs.deleteCharAt(CharIDs.length() - 1);//移除最后一个逗号
            //保存练习
            ContentValues values = new ContentValues();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  HH:mm", Locale.getDefault());
            String time = sdf.format(new Date());
            values.put("name", practiceData.getName());
            values.put("time", time);
            values.put("coverImgPath", practiceData.getCoverImgPath());
            values.put("charIDs", CharIDs.toString());
            long newID = db.insert("Record", null, values);
            if (newID != -1) {
                successNum++;
            }
            db.close();
        }
        boolean databaseSuccess = successNum == practiceData.charsData.size() + 1;
        //显示反馈信息
        if (fileSuccess && databaseSuccess) {
            Toast.makeText(context, R.string.save_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.save_error, Toast.LENGTH_SHORT).show();
        }
    }

    //删除练习记录
    public static void deleteRecord(Context context, PracticeData practiceData) {
        boolean fileSuccess = FileManager.deleteRecordImg(practiceData);//删除记录图片
        int successNum = 0;
        String[] idArray = practiceData.getCharIDs().split(",");
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (String id : idArray) {
                successNum += db.delete("WrittenChar", "id=?", new String[]{id});
            }
            successNum += db.delete("Record", "id=?",
                    new String[]{practiceData.getID() + ""});
        }
        boolean databaseSuccess = successNum == practiceData.charsData.size() + 1;
        //显示反馈信息
        if (fileSuccess && databaseSuccess) {
            Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.delete_error, Toast.LENGTH_SHORT).show();
        }
    }
}
