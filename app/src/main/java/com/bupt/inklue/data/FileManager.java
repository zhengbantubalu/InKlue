package com.bupt.inklue.data;

import android.content.Context;
import android.os.Environment;

import java.io.File;
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

    //初始化目录
    public static void initDirectory(Context context) {
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
                    return;
                }
            }
        }
    }

    //下载资源图片
    public static void downloadImg(Context context) {
        String dirPath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/practice/char";
        ArrayList<String> chars = new ArrayList<>(Arrays.asList(
                "土", "王", "五", "上", "下", "不", "之", "山", "廿", "四", "日", "石", "六", "天"));
        ArrayList<String> urlStrings = new ArrayList<>(Arrays.asList(
                "https://s21.ax1x.com/2024/04/05/pFqP31P.jpg",
                "https://s21.ax1x.com/2024/04/05/pFqPMtA.jpg",
                "https://s21.ax1x.com/2024/04/05/pFqPQfI.jpg",
                "https://s21.ax1x.com/2024/04/05/pFq9Z36.jpg",
                "https://s21.ax1x.com/2024/04/05/pFqPKkd.jpg",
                "https://s21.ax1x.com/2024/04/05/pFq9A41.jpg",
                "https://s21.ax1x.com/2024/04/05/pFqP86f.jpg",
                "https://s21.ax1x.com/2024/04/05/pFq9FE9.jpg",
                "https://s21.ax1x.com/2024/04/05/pFq9kNR.jpg",
                "https://s21.ax1x.com/2024/04/05/pFqFiM6.jpg",
                "https://s21.ax1x.com/2024/04/05/pFq9egK.jpg",
                "https://s21.ax1x.com/2024/04/05/pFq9mjO.jpg",
                "https://s21.ax1x.com/2024/04/05/pFq9V9x.jpg",
                "https://s21.ax1x.com/2024/04/05/pFqFFsK.jpg"));
        for (int i = 0; i < urlStrings.size(); i++) {
            try {
                String filePath = dirPath + "/" + chars.get(i) + ".jpg";
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
            } catch (IOException ignored) {
            }
        }
    }

    //移动书写图像存储位置
    public static void moveWrittenImg(Context context, PracticeData practiceData) {
        for (CharData charData : practiceData.charsData) {
            File srcFile = new File(charData.getWrittenImgPath());
            //重置书写图像路径
            charData.setWrittenImgPath(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                    "/record/char/" + srcFile.getName());
            File dstFile = new File(charData.getWrittenImgPath());
            //移动失败则返回
            if (!srcFile.renameTo(dstFile)) {
                return;
            }
        }
    }

    //清空指定目录
    public static boolean clearDir(String dirPath) {
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
}
