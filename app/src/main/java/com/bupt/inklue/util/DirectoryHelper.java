package com.bupt.inklue.util;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Environment;

import com.bupt.inklue.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

//目录帮助类
public class DirectoryHelper {

    //初始化目录
    public static void initDir(Context context) {
        ArrayList<String> dirs = new ArrayList<>(Arrays.asList(
                getPracticeHanZiDir(context),
                getPracticeCoverDir(context),
                getPracticeLogHanZiDir(context),
                getPracticeLogCoverDir(context)
        ));
        for (String dir : dirs) {
            File fileDir = new File(dir);
            //如果目录不存在则创建，如果创建失败则返回
            if (!fileDir.exists()) {
                if (!fileDir.mkdirs()) {
                    return;
                }
            }
        }
    }

    //清空指定目录
    public static boolean clearDir(String dir) {
        File fileDir = new File(dir);
        File[] files = fileDir.listFiles();
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

    //获取练习汉字图片路径
    public static String getPracticeHanZiDir(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                context.getString(R.string.han_zi_dir);
    }

    //获取记录汉字图片路径
    public static String getPracticeLogHanZiDir(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                context.getString(R.string.han_zi_log_dir);
    }

    //获取练习封面图片路径
    public static String getPracticeCoverDir(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                context.getString(R.string.practice_cover_dir);
    }

    //获取记录封面图片路径
    public static String getPracticeLogCoverDir(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                context.getString(R.string.practice_log_cover_dir);
    }

    //生成缓存目录JPG图片路径
    public static String generateCacheJPG(Context context) {
        return context.getExternalCacheDir() + "/" + generateTimeString() + ".jpg";
    }

    //生成当前时间字符串
    private static String generateTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.getDefault());
        return sdf.format(new Date());
    }
}
