package com.bupt.inklue.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.widget.Toast;

import com.bupt.inklue.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

//练习数据管理器
public class PracticeDataManager {

    //取得练习数据
    public static PracticeData getPracticeData(Context context, long id) {
        PracticeData practiceData = new PracticeData();
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Practice WHERE id = " + id, null);
            int nameIndex = cursor.getColumnIndex("name");
            int coverImgPathIndex = cursor.getColumnIndex("coverImgPath");
            int charIDsIndex = cursor.getColumnIndex("charIDs");
            if (cursor.moveToFirst()) {
                practiceData.setName(cursor.getString(nameIndex));
                practiceData.setCoverImgPath(cursor.getString(coverImgPathIndex));
                practiceData.setCharIDs(cursor.getString(charIDsIndex));
            }
            cursor.close();
        }
        practiceData.charsData = getStdCharsData(context, practiceData.getCharIDs());
        return practiceData;
    }

    //取得标准汉字数据
    private static ArrayList<CharData> getStdCharsData(Context context, String charIDs) {
        Log.d("appTest", charIDs);
        ArrayList<CharData> charsData = new ArrayList<>();
        String[] idArray = charIDs.split(",");
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

    //取得记录数据
    public static PracticeData getRecordData(Context context, long id) {
        PracticeData recordData = new PracticeData();
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Record WHERE id = " + id, null);
            int nameIndex = cursor.getColumnIndex("name");
            int charIDsIndex = cursor.getColumnIndex("charIDs");
            if (cursor.moveToFirst()) {
                recordData.setName(cursor.getString(nameIndex));
                recordData.setCharIDs(cursor.getString(charIDsIndex));
            }
            cursor.close();
        }
        recordData.charsData = getWrittenCharsData(context, recordData.getCharIDs());
        return recordData;
    }

    //取得书写汉字数据
    private static ArrayList<CharData> getWrittenCharsData(Context context, String charIDs) {
        Log.d("appTest", charIDs);
        ArrayList<CharData> charsData = new ArrayList<>();
        String[] idArray = charIDs.split(",");
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
        FileManager.moveWrittenImg(context, practiceData);//移动书写图像存储位置
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault());
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
        //显示反馈信息
        if (successNum == practiceData.charsData.size() + 1) {
            Toast.makeText(context, R.string.save_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.save_error, Toast.LENGTH_SHORT).show();
        }
    }
}
