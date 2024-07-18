package com.bupt.inklue.util;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Environment;

import java.util.Date;
import java.util.Locale;

//文件路径生成器
public class FilePathGenerator {

    //生成缓存目录JPG图片路径
    public static String generateCacheJPG(Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.getDefault());
        String time = sdf.format(new Date());
        return context.getExternalCacheDir() + "/" + time + ".jpg";
    }

    //生成练习封面JPG图片路径
    public static String generatePracticeCoverJPG(Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.getDefault());
        String time = sdf.format(new Date());
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                "/practice/cover/" + time + ".jpg";
    }
}
