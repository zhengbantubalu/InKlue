package com.bupt.inklue.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//SQLite数据库帮助类
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS StdChar " +
                "(id INTEGER PRIMARY KEY, name TEXT, stdImgPath TEXT, writtenImgPath TEXT, " +
                "advice TEXT, score INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Practice " +
                "(id INTEGER PRIMARY KEY, name TEXT, coverImgPath TEXT, characterIDs TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Record " +
                "(id INTEGER PRIMARY KEY, name TEXT, coverImgPath TEXT, characterIDs TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS WrittenChar " +
                "(id INTEGER PRIMARY KEY, name TEXT, stdImgPath TEXT, writtenImgPath TEXT, " +
                "advice TEXT, score INTEGER)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
