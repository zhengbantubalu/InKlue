package com.bupt.inklue.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bupt.inklue.data.db.DatabaseHelper;
import com.bupt.inklue.data.pojo.HanZi;
import com.bupt.inklue.data.pojo.Practice;

import java.util.ArrayList;

public class HanZiLogRepository {

    public static ArrayList<HanZi> getPracticeLogHanZiList(Context context, Practice practice) {
        ArrayList<HanZi> hanZiLogList = new ArrayList<>();
        String[] idArray = practice.getHanZiLogIds().split(",");
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (String id : idArray) {
                Cursor cursor = db.query("han_zi_log", null, "id = ?",
                        new String[]{id}, null, null, null);
                int codeIndex = cursor.getColumnIndex("code");
                int nameIndex = cursor.getColumnIndex("name");
                int pathIndex = cursor.getColumnIndex("path");
                int writtenPathIndex = cursor.getColumnIndex("written_path");
                int feedbackPathIndex = cursor.getColumnIndex("feedback_path");
                int scoreIndex = cursor.getColumnIndex("score");
                int adviceIndex = cursor.getColumnIndex("advice");
                if (cursor.moveToFirst()) {
                    do {
                        HanZi hanZi = new HanZi();
                        hanZi.setCode(cursor.getString(codeIndex));
                        hanZi.setName(cursor.getString(nameIndex));
                        hanZi.setPath(cursor.getString(pathIndex));
                        hanZi.setWrittenPath(cursor.getString(writtenPathIndex));
                        hanZi.setFeedbackPath(cursor.getString(feedbackPathIndex));
                        hanZi.setScore(cursor.getString(scoreIndex));
                        hanZi.setAdvice(cursor.getString(adviceIndex));
                        hanZiLogList.add(hanZi);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
            db.close();
        }
        return hanZiLogList;
    }

    public static boolean savePracticeLogHanZi(Context context, Practice practiceLog) {
        int successNum = 0;
        StringBuilder hanZiLogIds = new StringBuilder();
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (HanZi hanZi : practiceLog.hanZiList) {
                ContentValues values = new ContentValues();
                values.put("code", hanZi.getCode());
                values.put("name", hanZi.getName());
                values.put("path", hanZi.getPath());
                values.put("written_path", hanZi.getWrittenPath());
                values.put("feedback_path", hanZi.getFeedbackPath());
                values.put("score", hanZi.getScore());
                values.put("advice", hanZi.getAdvice());
                long newID = db.insert("han_zi_log", null, values);
                if (newID != -1) {
                    successNum++;
                    hanZiLogIds.append(newID).append(",");
                }
            }
            //移除最后一个逗号
            if (hanZiLogIds.length() != 0) {
                hanZiLogIds.deleteCharAt(hanZiLogIds.length() - 1);
            }
            db.close();
        }
        if (successNum == practiceLog.hanZiList.size()) {
            practiceLog.setHanZiLogIds(hanZiLogIds.toString());
            return true;
        } else {
            return false;
        }
    }

    public static boolean deletePracticeLogHanZi(Context context, Practice practiceLog) {
        int successNum = 0;
        String[] idArray = practiceLog.getHanZiLogIds().split(",");
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (String id : idArray) {
                successNum += db.delete("han_zi_log", "id=?", new String[]{id});
            }
            db.close();
        }
        return successNum == practiceLog.hanZiList.size();
    }
}
