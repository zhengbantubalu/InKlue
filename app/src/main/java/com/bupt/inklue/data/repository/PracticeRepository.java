package com.bupt.inklue.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bupt.inklue.data.db.DatabaseHelper;
import com.bupt.inklue.data.pojo.HanZi;
import com.bupt.inklue.data.pojo.Practice;
import com.bupt.inklue.util.DirectoryHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class PracticeRepository {

    public static void putInitialPracticeData(Context context, JSONArray practicesData) {
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (int i = practicesData.length() - 1; i >= 0; i--) {
                JSONObject practiceData = practicesData.getJSONObject(i);
                String name = practiceData.getString("name");
                String coverPath = DirectoryHelper.getPracticeCoverDir(context) + "/" + name + ".jpg";
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("cover_path", coverPath);
                values.put("han_zi_codes", practiceData.getString("hanZiCodes"));
                db.insert("practice", null, values);
            }
            db.close();
        } catch (JSONException ignored) {
        }
    }

    public static ArrayList<Practice> getPracticeList(Context context) {
        ArrayList<Practice> practiceList = new ArrayList<>();
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("practice", null, null,
                    null, null, null, null);
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int coverPathIndex = cursor.getColumnIndex("cover_path");
            int hanZiCodesIndex = cursor.getColumnIndex("han_zi_codes");
            if (cursor.moveToFirst()) {
                do {
                    Practice practice = new Practice();
                    practice.setId(cursor.getLong(idIndex));
                    practice.setName(cursor.getString(nameIndex));
                    practice.setCoverPath(cursor.getString(coverPathIndex));
                    practice.setHanZiCodes(cursor.getString(hanZiCodesIndex));
                    practiceList.add(practice);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        Collections.reverse(practiceList);
        return practiceList;
    }

    public static boolean createPractice(Context context, Practice practice) {
        boolean isSuccess;
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", practice.getName());
            values.put("cover_path", practice.getCoverPath());
            values.put("han_zi_codes", practice.getHanZiCodes());
            isSuccess = db.insert("practice", null, values) != -1;
            db.close();
        }
        return isSuccess;
    }

    public static boolean renamePractice(Context context, Practice practice) {
        boolean isSuccess;
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", practice.getName());
            isSuccess = db.update("practice", values, "id = ?",
                    new String[]{practice.getId() + ""}) == 1;
            db.close();
        }
        return isSuccess;
    }

    public static boolean addHanZiIntoPractice(Context context, Practice practice, HanZi hanZi) {
        boolean isSuccess;
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            String newHanZiCodes;
            if (practice.getHanZiCodes().isEmpty()) {
                newHanZiCodes = hanZi.getCode();
            } else {
                newHanZiCodes = practice.getHanZiCodes() + "," + hanZi.getCode();
            }
            values.put("han_zi_codes", newHanZiCodes);
            isSuccess = db.update("practice", values, "id = ?",
                    new String[]{practice.getId() + ""}) == 1;
            db.close();
        }
        return isSuccess;
    }

    public static boolean deletePractice(Context context, Practice practice) {
        boolean isSuccess;
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            isSuccess = db.delete("practice", "id=?",
                    new String[]{practice.getId() + ""}) == 1;
            db.close();
        }
        return isSuccess;
    }
}
