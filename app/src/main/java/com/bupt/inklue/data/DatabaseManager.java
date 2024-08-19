package com.bupt.inklue.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.bupt.inklue.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//数据库管理器
public class DatabaseManager {

    //初始化数据库
    public static void initDatabase(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS StdChar " +
                "(id INTEGER PRIMARY KEY, className TEXT, name TEXT, url TEXT, stdImgPath TEXT, " +
                "style TEXT, era TEXT, author TEXT, copybook TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS WrittenChar " +
                "(id INTEGER PRIMARY KEY, name TEXT, className TEXT, stdImgPath TEXT, " +
                "writtenImgPath TEXT, feedbackImgPath TEXT, score TEXT, advice TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Practice " +
                "(id INTEGER PRIMARY KEY, name TEXT, coverImgPath TEXT, charCodes TEXT, charIDs TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Record " +
                "(id INTEGER PRIMARY KEY, name TEXT, time TEXT, coverImgPath TEXT, charIDs TEXT)");
    }

    //重置数据库
    public static void resetDatabase(Context context) {
        //删除数据库
        context.deleteDatabase("database.db");
        //清空记录目录
        FileManager.clearDirectory(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/record/char");
        FileManager.clearDirectory(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/record/cover");
        //获取初始汉字数据
        JSONArray charsData = getInitialData(context, context.getString(R.string.han_zi_path));
        //获取初始练习数据
        JSONArray practicesData = getInitialData(context, context.getString(R.string.practice_path));
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //初始化数据库
            initDatabase(db);
            //置入初始汉字数据
            putInitialCharData(db, charsData,
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/practice");
            //置入初始练习数据
            putInitialPracticeData(db, practicesData,
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/practice");
            db.close();
        }
    }

    //获取初始数据
    private static JSONArray getInitialData(Context context, String path) {
        try {
            URL url = new URL(context.getString(R.string.server_url) + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            inputStream.close();
            connection.disconnect();
            return new JSONArray(result.toString());
        } catch (IOException | JSONException ignored) {
            return new JSONArray();
        }
    }

    //置入初始汉字数据
    private static void putInitialCharData(SQLiteDatabase db, JSONArray charsData, String dirPath) {
        for (int i = 0; i < charsData.length(); i++) {
            try {
                JSONObject charData = charsData.getJSONObject(i);
                String className = charData.getString("code");
                String stdImgPath = dirPath + "/char/" + className + ".jpg";
                ContentValues values = new ContentValues();
                values.put("name", charData.getString("name"));
                values.put("className", className);
                values.put("style", charData.getString("style"));
                values.put("era", charData.getString("era"));
                values.put("author", charData.getString("artist"));
                values.put("copybook", charData.getString("work"));
                values.put("stdImgPath", stdImgPath);
                values.put("url", charData.getString("url"));
                db.insert("StdChar", null, values);
            } catch (JSONException ignored) {
            }
        }
    }

    //置入初始练习数据
    private static void putInitialPracticeData(SQLiteDatabase db, JSONArray practicesData, String dirPath) {
        for (int i = practicesData.length() - 1; i >= 0; i--) {
            try {
                JSONObject practiceData = practicesData.getJSONObject(i);
                String name = practiceData.getString("name");
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("coverImgPath", dirPath + "/cover/" + name + ".jpg");
                values.put("charCodes", practiceData.getString("hanZiCodes"));
                values.put("charIDs", practiceData.getString("hanZiIds"));
                db.insert("Practice", null, values);
            } catch (JSONException ignored) {
            }
        }
    }
}
