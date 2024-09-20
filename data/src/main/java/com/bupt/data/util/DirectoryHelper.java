package com.bupt.data.util;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Environment;

import java.util.Date;
import java.util.Locale;

//目录帮助类
public class DirectoryHelper {

    //获取练习汉字图片路径
    public static String getPracticeHanZiDir(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + Constants.DIR_HAN_ZI;
    }

    //获取记录汉字图片路径
    public static String getPracticeLogHanZiDir(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + Constants.DIR_HAN_ZI_LOG;
    }

    //获取练习封面图片路径
    public static String getPracticeCoverDir(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + Constants.DIR_PRACTICE_COVER;
    }

    //获取记录封面图片路径
    public static String getPracticeLogCoverDir(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + Constants.DIR_PRACTICE_LOG_COVER;
    }

    //生成练习封面JPG图片路径
    public static String generatePracticeCoverJPG(Context context) {
        return getPracticeCoverDir(context) + "/" + generateTimeString() + ".jpg";
    }

    //生成记录封面JPG图片路径
    public static String generatePracticeLogCoverJPG(Context context) {
        return getPracticeLogCoverDir(context) + "/" + generateTimeString() + ".jpg";
    }

    //生成当前时间字符串
    private static String generateTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.getDefault());
        return sdf.format(new Date());
    }
}
