package com.bupt.inklue.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//账户数据库帮助类
public class SqliteOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Sqlite.db";

    private static final String create_user = "create table user(name varchar(32), password varchar(32))";

    public SqliteOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_user);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //注册
    public long register(UserData u) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", u.getAccount());
        cv.put("password", u.getPassword());
        return db.insert("user", null, cv);
    }

    //登录
    public boolean load(String name, String password) {
        SQLiteDatabase db = getWritableDatabase();
        boolean result;
        Cursor user = db.query("user", null, "name like ?",
                new String[]{name}, null, null, null);
        if (user != null) {
            while (user.moveToNext()) {
                String password1 = user.getString(1);
                result = password1.equals(password);
                return result;//是否能交换
            }
        }
        return false;
    }
}
