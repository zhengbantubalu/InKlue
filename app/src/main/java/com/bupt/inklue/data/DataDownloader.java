package com.bupt.inklue.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

//数据下载器
public class DataDownloader {

    //下载资源图片
    public static void downloadImg(String dirPath) {
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
}
