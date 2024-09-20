package com.bupt.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bupt.data.db.DatabaseHelper;
import com.bupt.data.pojo.HanZi;
import com.bupt.data.pojo.Practice;
import com.bupt.data.util.Constants;
import com.bupt.data.util.DirectoryHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HanZiRepository {

    public static void putInitialHanZiData(Context context, JSONArray hanZiJSONArray) {
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (int i = 0; i < hanZiJSONArray.length(); i++) {
                JSONObject hanZiJSONObject = hanZiJSONArray.getJSONObject(i);
                String code = hanZiJSONObject.getString("code");
                String path = DirectoryHelper.getPracticeHanZiDir(context) + "/" + code + ".jpg";
                ContentValues values = new ContentValues();
                values.put("code", code);
                values.put("name", hanZiJSONObject.getString("name"));
                values.put("url", hanZiJSONObject.getString("url"));
                values.put("path", path);
                values.put("work", hanZiJSONObject.getString("work"));
                values.put("artist", hanZiJSONObject.getString("artist"));
                values.put("era", hanZiJSONObject.getString("era"));
                values.put("style", hanZiJSONObject.getString("style"));
                db.insert(Constants.TABLE_HAN_ZI, null, values);
            }
            db.close();
        } catch (JSONException ignored) {
        }
    }

    public static ArrayList<HanZi> getPracticeHanZiList(Context context, Practice practice) {
        ArrayList<HanZi> hanZiList = new ArrayList<>();
        String[] codeArray = practice.getHanZiCodes().split(",");
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (String code : codeArray) {
                Cursor cursor = db.query(Constants.TABLE_HAN_ZI, null, "code = ?",
                        new String[]{code}, null, null, null);
                int nameIndex = cursor.getColumnIndex("name");
                int pathIndex = cursor.getColumnIndex("path");
                if (cursor.moveToFirst()) {
                    do {
                        HanZi hanZi = new HanZi();
                        hanZi.setCode(code);
                        hanZi.setName(cursor.getString(nameIndex));
                        hanZi.setPath(cursor.getString(pathIndex));
                        hanZiList.add(hanZi);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
            db.close();
        }
        return hanZiList;
    }

    public static ArrayList<HanZi> getMatchedHanZiList(Context context, HanZi filter) {
        ArrayList<HanZi> hanZiList = new ArrayList<>();
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query(Constants.TABLE_HAN_ZI, null, null,
                    null, null, null, null);
            int codeIndex = cursor.getColumnIndex("code");
            int nameIndex = cursor.getColumnIndex("name");
            int pathIndex = cursor.getColumnIndex("path");
            int workIndex = cursor.getColumnIndex("work");
            int artistIndex = cursor.getColumnIndex("artist");
            int eraIndex = cursor.getColumnIndex("era");
            int styleIndex = cursor.getColumnIndex("style");
            if (cursor.moveToFirst()) {
                do {
                    HanZi hanZi = new HanZi();
                    hanZi.setCode(cursor.getString(codeIndex));
                    hanZi.setName(cursor.getString(nameIndex));
                    hanZi.setPath(cursor.getString(pathIndex));
                    hanZi.setWork(cursor.getString(workIndex));
                    hanZi.setArtist(cursor.getString(artistIndex));
                    hanZi.setEra(cursor.getString(eraIndex));
                    hanZi.setStyle(cursor.getString(styleIndex));
                    if (hanZi.match(filter)) {
                        hanZiList.add(hanZi);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return hanZiList;
    }

    public static ArrayList<HanZi> getHanZiListForDownloadImg(Context context) {
        ArrayList<HanZi> hanZiList = new ArrayList<>();
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query(Constants.TABLE_HAN_ZI, null, null,
                    null, null, null, null);
            int codeIndex = cursor.getColumnIndex("code");
            int urlIndex = cursor.getColumnIndex("url");
            if (cursor.moveToFirst()) {
                do {
                    HanZi hanZi = new HanZi();
                    hanZi.setCode(cursor.getString(codeIndex));
                    hanZi.setUrl(cursor.getString(urlIndex));
                    hanZiList.add(hanZi);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return hanZiList;
    }
}
