package com.bupt.inklue.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.bupt.inklue.data.DatabaseHelper;

import java.util.ArrayList;
import java.util.Arrays;

//数据库管理器
public class DatabaseManager {

    //重置数据库
    public static void resetDatabase(Context context) {
        ArrayList<String> chars = new ArrayList<>(Arrays.asList(
                "土", "王", "五", "上", "下", "不", "之", "山", "廿", "四", "日", "石", "六", "天"));
        context.deleteDatabase("database.db");
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("CREATE TABLE IF NOT EXISTS StdChar " +
                    "(id INTEGER PRIMARY KEY, name TEXT, stdImgPath TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS WrittenChar " +
                    "(id INTEGER PRIMARY KEY, name TEXT, stdImgPath TEXT, writtenImgPath TEXT, " +
                    "score TEXT, advice TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS Practice " +
                    "(id INTEGER PRIMARY KEY, name TEXT, coverImgPath TEXT, charIDs TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS Record " +
                    "(id INTEGER PRIMARY KEY, name TEXT, coverImgPath TEXT, charIDs TEXT)");
            for (String c : chars) {
                String stdImgPath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                        "/" + c + ".jpg";
                ContentValues values = new ContentValues();
                values.put("name", c);
                values.put("stdImgPath", stdImgPath);
                long newID = db.insert("StdChar", null, values);
                if (newID != -1) {
                    Log.d("appTest", "数据插入成功，新行ID为: " + newID);
                } else {
                    Log.e("appTest", "数据插入失败");
                }
            }
            for (int i = 0; i < 10; i++) {
                ContentValues values = new ContentValues();
                String name = "峄山碑";
                String coverImgPath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                        "/" + name + ".jpg";
                values.put("name", name);
                values.put("coverImgPath", coverImgPath);
                values.put("charIDs", "1,2,3,4,5,6,7,8,9,10,11,12,13,14");
                long newID = db.insert("Practice", null, values);
                if (newID != -1) {
                    Log.d("appTest", "数据插入成功，新行ID为: " + newID);
                } else {
                    Log.e("appTest", "数据插入失败");
                }
            }
            db.close();
        }
    }
}
