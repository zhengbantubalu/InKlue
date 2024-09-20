package com.bupt.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bupt.data.util.Constants;

//数据库管理器
public class DatabaseManager {

    public static void initDatabase(Context context) {
        context.deleteDatabase(Constants.DATABASE_NAME);
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("CREATE TABLE IF NOT EXISTS han_zi " +
                    "(id INTEGER PRIMARY KEY, code TEXT UNIQUE, name TEXT, url TEXT, path TEXT, " +
                    "work TEXT, artist TEXT, era TEXT, style TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS han_zi_log " +
                    "(id INTEGER PRIMARY KEY, code TEXT, name TEXT, path TEXT, " +
                    "written_path TEXT, feedback_path TEXT, score TEXT, advice TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS practice " +
                    "(id INTEGER PRIMARY KEY, name TEXT, cover_path TEXT, han_zi_codes TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS practice_log " +
                    "(id INTEGER PRIMARY KEY, name TEXT, cover_path TEXT, time TEXT, " +
                    "han_zi_codes TEXT, han_zi_log_ids TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS user " +
                    "(id INTEGER PRIMARY KEY, account TEXT, name TEXT, email TEXT, " +
                    "avatar_url TEXT, avatar_path TEXT)");
            db.close();
        }
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(Constants.DATABASE_NAME);
    }
}
