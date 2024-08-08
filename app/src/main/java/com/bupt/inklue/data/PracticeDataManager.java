package com.bupt.inklue.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.widget.Toast;

import com.bupt.inklue.R;
import com.bupt.inklue.util.BitmapProcessor;
import com.bupt.inklue.util.FilePathGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

//练习数据管理器
public class PracticeDataManager {

    //取得标准汉字数据
    public static ArrayList<CharData> getStdCharsData(Context context, PracticeData practiceData) {
        ArrayList<CharData> charsData = new ArrayList<>();
        String[] idArray = practiceData.getCharIDs().split(",");
        if (idArray[0].isEmpty()) {
            return charsData;
        }
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (String id : idArray) {
                Cursor cursor = db.rawQuery("SELECT * FROM StdChar WHERE id = " +
                        id, null);
                int nameIndex = cursor.getColumnIndex("name");
                int classNameIndex = cursor.getColumnIndex("className");
                int stdImgPathIndex = cursor.getColumnIndex("stdImgPath");
                if (cursor.moveToFirst()) {
                    do {
                        CharData charData = new CharData();
                        charData.setID(Long.parseLong(id));
                        charData.setName(cursor.getString(nameIndex));
                        charData.setClassName(cursor.getString(classNameIndex));
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

    //取得书写汉字数据
    public static ArrayList<CharData> getWrittenCharsData(Context context, PracticeData practiceData) {
        ArrayList<CharData> charsData = new ArrayList<>();
        String[] idArray = practiceData.getCharIDs().split(",");
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (String id : idArray) {
                Cursor cursor = db.rawQuery("SELECT * FROM WrittenChar WHERE id = " +
                        id, null);
                int nameIndex = cursor.getColumnIndex("name");
                int stdImgPathIndex = cursor.getColumnIndex("stdImgPath");
                int writtenImgPathIndex = cursor.getColumnIndex("writtenImgPath");
                int feedbackImgPathIndex = cursor.getColumnIndex("feedbackImgPath");
                int scoreIndex = cursor.getColumnIndex("score");
                int adviceIndex = cursor.getColumnIndex("advice");
                if (cursor.moveToFirst()) {
                    do {
                        CharData charData = new CharData();
                        charData.setID(Long.parseLong(id));
                        charData.setName(cursor.getString(nameIndex));
                        charData.setStdImgPath(cursor.getString(stdImgPathIndex));
                        charData.setWrittenImgPath(cursor.getString(writtenImgPathIndex));
                        charData.setFeedbackImgPath(cursor.getString(feedbackImgPathIndex));
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

    //创建练习
    public static void createPractice(Context context, String name) {
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String coverImgPath = FilePathGenerator.generatePracticeCoverJPG(context);
            Bitmap coverBitmap = BitmapProcessor.createCover(new PracticeData(), true);
            FileManager.saveBitmap(coverBitmap, coverImgPath);
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("coverImgPath", coverImgPath);
            values.put("charIDs", "");
            long newID = db.insert("Practice", null, values);
            //显示反馈信息
            if (newID != -1) {
                Toast.makeText(context, R.string.create_success, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, R.string.create_error, Toast.LENGTH_SHORT).show();
            }
            db.close();
        }
    }

    //重命名练习
    public static void renamePractice(Context context, PracticeData practiceData, String name) {
        boolean success;
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", name);
            success = db.update("Practice", values, "id = ?",
                    new String[]{practiceData.getID() + ""}) == 1;
            db.close();
        }
        //显示反馈信息
        if (success) {
            Toast.makeText(context, R.string.rename_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.rename_error, Toast.LENGTH_SHORT).show();
        }
    }

    //向练习中添加汉字
    public static void addCharIntoPractice(Context context, PracticeData practiceData,
                                           long charID, String charImgPath) {
        //创建新的练习封面
        CharData charData = new CharData();
        charData.setStdImgPath(charImgPath);
        practiceData.charsData = getStdCharsData(context, practiceData);
        practiceData.charsData.add(charData);
        Bitmap coverBitmap = BitmapProcessor.createCover(practiceData, true);
        FileManager.saveBitmap(coverBitmap, practiceData.getCoverImgPath());
        //将汉字ID插入数据库
        boolean success;
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            if (practiceData.getCharIDs().isEmpty()) {
                values.put("charIDs", charID);
            } else {
                values.put("charIDs", practiceData.getCharIDs() + "," + charID);
            }
            success = db.update("Practice", values, "id = ?",
                    new String[]{practiceData.getID() + ""}) == 1;
            db.close();
        }
        //显示反馈信息
        if (success) {
            Toast.makeText(context, R.string.add_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.add_error, Toast.LENGTH_SHORT).show();
        }
    }

    //删除练习
    public static void deletePractice(Context context, PracticeData practiceData) {
        boolean fileSuccess = FileManager.deletePracticeCover(practiceData);//删除练习封面
        boolean databaseSuccess;
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            databaseSuccess = db.delete("Practice", "id=?",
                    new String[]{practiceData.getID() + ""}) == 1;
            db.close();
        }
        //显示反馈信息
        if (fileSuccess && databaseSuccess) {
            Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.delete_error, Toast.LENGTH_SHORT).show();
        }
    }

    //保存练习记录
    public static void saveRecord(Context context, PracticeData practiceData) {
        boolean fileSuccess = FileManager.moveRecordImg(context, practiceData);//移动记录图片存储位置
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
                values.put("feedbackImgPath", charData.getFeedbackImgPath());
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  HH:mm", Locale.getDefault());
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
        boolean databaseSuccess = successNum == practiceData.charsData.size() + 1;
        //显示反馈信息
        if (fileSuccess && databaseSuccess) {
            Toast.makeText(context, R.string.save_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.save_error, Toast.LENGTH_SHORT).show();
        }
    }

    //删除练习记录
    public static void deleteRecord(Context context, PracticeData recordData) {
        boolean fileSuccess = FileManager.deleteRecordImg(recordData);//删除记录图片
        int successNum = 0;
        String[] idArray = recordData.getCharIDs().split(",");
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (String id : idArray) {
                successNum += db.delete("WrittenChar", "id=?", new String[]{id});
            }
            successNum += db.delete("Record", "id=?",
                    new String[]{recordData.getID() + ""});
            db.close();
        }
        boolean databaseSuccess = successNum == recordData.charsData.size() + 1;
        //显示反馈信息
        if (fileSuccess && databaseSuccess) {
            Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.delete_error, Toast.LENGTH_SHORT).show();
        }
    }
}
