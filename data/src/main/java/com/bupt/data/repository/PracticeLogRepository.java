package com.bupt.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;

import com.bupt.data.db.DatabaseHelper;
import com.bupt.data.pojo.Practice;
import com.bupt.data.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class PracticeLogRepository {

    public static ArrayList<Practice> getPracticeLogList(Context context) {
        ArrayList<Practice> practiceLogList = new ArrayList<>();
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query(Constants.TABLE_PRACTICE_LOG, null, null,
                    null, null, null, null);
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int coverPathIndex = cursor.getColumnIndex("cover_path");
            int timeIndex = cursor.getColumnIndex("time");
            int hanZiLogIdsIndex = cursor.getColumnIndex("han_zi_log_ids");
            if (cursor.moveToFirst()) {
                do {
                    Practice practice = new Practice();
                    practice.setId(cursor.getLong(idIndex));
                    practice.setName(cursor.getString(nameIndex));
                    practice.setCoverPath(cursor.getString(coverPathIndex));
                    practice.setTime(cursor.getString(timeIndex));
                    practice.setHanZiLogIds(cursor.getString(hanZiLogIdsIndex));
                    practiceLogList.add(practice);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        Collections.reverse(practiceLogList);
        return practiceLogList;
    }

    public static boolean savePracticeLog(Context context, Practice practiceLog) {
        boolean isSuccess;
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  HH:mm", Locale.getDefault());
            String time = sdf.format(new Date());
            values.put("name", practiceLog.getName());
            values.put("cover_path", practiceLog.getCoverPath());
            values.put("time", time);
            values.put("han_zi_codes", practiceLog.getHanZiCodes());
            values.put("han_zi_log_ids", practiceLog.getHanZiLogIds());
            isSuccess = db.insert(Constants.TABLE_PRACTICE_LOG, null, values) != -1;
            db.close();
        }
        return isSuccess;
    }

    public static boolean deletePracticeLog(Context context, Practice practiceLog) {
        boolean isSuccess;
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            isSuccess = db.delete(Constants.TABLE_PRACTICE_LOG, "id=?",
                    new String[]{practiceLog.getId() + ""}) == 1;
            db.close();
        }
        return isSuccess;
    }
}
