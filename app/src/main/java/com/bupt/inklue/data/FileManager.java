package com.bupt.inklue.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.ProgressBar;

import com.bupt.inklue.util.BitmapProcessor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

//文件管理器
public class FileManager {

    //下载资源图片
    public static void downloadImg(Context context, ProgressBar progressBar) {
        String dirPath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/practice/char";
        ArrayList<CharData> charsData = getCharsData(context);
        for (int i = 0; i < charsData.size(); i++) {
            try {
                String filePath = dirPath + "/" + charsData.get(i).getClassName() + ".jpg";
                URL url = new URL(charsData.get(i).getStdImgPath());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                OutputStream outputStream = Files.newOutputStream(Paths.get(filePath));
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();
                connection.disconnect();
                //更新进度条
                progressBar.setProgress((i + 1) * 100 / charsData.size());
            } catch (IOException ignored) {
            }
        }
    }

    //获取下载图片所需的汉字数据
    private static ArrayList<CharData> getCharsData(Context context) {
        ArrayList<CharData> charsData = new ArrayList<>();
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("StdChar", null, null,
                    null, null, null, null);
            int classNameIndex = cursor.getColumnIndex("className");
            int urlIndex = cursor.getColumnIndex("url");
            if (cursor.moveToFirst()) {
                do {
                    CharData charData = new CharData();
                    charData.setClassName(cursor.getString(classNameIndex));
                    charData.setStdImgPath(cursor.getString(urlIndex));
                    charsData.add(charData);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return charsData;
    }

    //创建练习封面
    public static void createPracticesCover(Context context) {
        ArrayList<PracticeData> practicesData = getPracticesData(context);
        for (int i = 0; i < practicesData.size(); i++) {
            PracticeData practiceData = practicesData.get(i);
            String[] codeArray = practiceData.getCharIDs().split(",");
            for (String code : codeArray) {
                CharData charData = new CharData();
                charData.setStdImgPath(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                        "/practice/char/" + code + ".jpg");
                practiceData.charsData.add(charData);
            }
            Bitmap coverBitmap = BitmapProcessor.createCover(practiceData, true);
            FileManager.saveBitmap(coverBitmap, practiceData.getCoverImgPath());
        }
    }

    //获取创建封面所需的练习数据
    private static ArrayList<PracticeData> getPracticesData(Context context) {
        ArrayList<PracticeData> practicesData = new ArrayList<>();
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("Practice", null, null,
                    null, null, null, null);
            int coverImgPathIndex = cursor.getColumnIndex("coverImgPath");
            int charCodesIndex = cursor.getColumnIndex("charCodes");
            if (cursor.moveToFirst()) {
                do {
                    PracticeData practiceData = new PracticeData();
                    practiceData.setCoverImgPath(cursor.getString(coverImgPathIndex));
                    practiceData.setCharIDs(cursor.getString(charCodesIndex));
                    practicesData.add(practiceData);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return practicesData;
    }

    //初始化目录
    public static boolean initDirectory(Context context) {
        String dirPath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "";
        ArrayList<String> directoryPaths = new ArrayList<>(Arrays.asList(
                dirPath + "/practice/char",
                dirPath + "/practice/cover",
                dirPath + "/record/char",
                dirPath + "/record/cover"
        ));
        for (String directoryPath : directoryPaths) {
            File directory = new File(directoryPath);
            //如果目录不存在则创建，如果创建失败则返回
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    return false;
                }
            }
        }
        return true;
    }

    //清空指定目录
    public static boolean clearDirectory(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                //删除失败则返回
                if (!file.delete()) {
                    return false;
                }
            }
        }
        return true;
    }

    //移动记录图片存储位置
    public static boolean moveRecordImg(Context context, PracticeData practiceData) {
        //移动封面
        File coverSrcFile = new File(practiceData.getCoverImgPath());
        //重置封面路径
        practiceData.setCoverImgPath(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                "/record/cover/" + coverSrcFile.getName());
        File coverDstFile = new File(practiceData.getCoverImgPath());
        //移动失败则返回
        if (!coverSrcFile.renameTo(coverDstFile)) {
            return false;
        }
        //移动书写图像
        for (CharData charData : practiceData.charsData) {
            File srcFile = new File(charData.getWrittenImgPath());
            //重置书写图像路径
            charData.setWrittenImgPath(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                    "/record/char/" + srcFile.getName());
            File dstFile = new File(charData.getWrittenImgPath());
            //移动失败则返回
            if (!srcFile.renameTo(dstFile)) {
                return false;
            }
        }
        //移动反馈图像
        for (CharData charData : practiceData.charsData) {
            File srcFile = new File(charData.getFeedbackImgPath());
            //重置反馈图像路径
            charData.setFeedbackImgPath(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                    "/record/char/" + srcFile.getName());
            File dstFile = new File(charData.getFeedbackImgPath());
            //移动失败则返回
            if (!srcFile.renameTo(dstFile)) {
                return false;
            }
        }
        return true;
    }

    //删除练习封面
    public static boolean deletePracticeCover(PracticeData practiceData) {
        File cover = new File(practiceData.getCoverImgPath());
        return cover.delete();
    }

    //删除记录图片
    public static boolean deleteRecordImg(PracticeData recordData) {
        //删除封面
        File cover = new File(recordData.getCoverImgPath());
        //删除失败则返回
        if (!cover.delete()) {
            return false;
        }
        //删除书写图像
        for (CharData charData : recordData.charsData) {
            File writtenImg = new File(charData.getWrittenImgPath());
            //删除失败则返回
            if (!writtenImg.delete()) {
                return false;
            }
        }
        //删除反馈图像
        for (CharData charData : recordData.charsData) {
            File feedbackImg = new File(charData.getFeedbackImgPath());
            //删除失败则返回
            if (!feedbackImg.delete()) {
                return false;
            }
        }
        return true;
    }

    //保存Bitmap
    public static void saveBitmap(Bitmap bitmap, String filePath) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (IOException ignored) {
        }
    }
}
