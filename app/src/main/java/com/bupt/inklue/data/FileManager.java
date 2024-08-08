package com.bupt.inklue.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.ProgressBar;

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
        ArrayList<String> classNames = InitialData.getClassNames();
        ArrayList<String> urlStrings = InitialData.getUrlStrings();
        for (int i = 0; i < urlStrings.size(); i++) {
            try {
                String filePath = dirPath + "/" + classNames.get(i) + ".jpg";
                URL url = new URL(urlStrings.get(i));
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
                progressBar.setProgress((i + 1) * 100 / urlStrings.size());
            } catch (IOException ignored) {
            }
        }
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
