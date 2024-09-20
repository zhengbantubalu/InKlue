package com.bupt.data.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import com.bupt.data.pojo.Practice;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

//Bitmap帮助类
public class BitmapHelper {

    //保存Bitmap
    public static boolean saveBitmap(Bitmap bitmap, String filePath) {
        boolean isSuccess;
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            return isSuccess;
        } catch (IOException ignored) {
            return false;
        }
    }

    //创建练习封面
    //isStd为真则以标准图像创建，否则以书写图像创建
    public static Bitmap createCover(Practice practice, boolean isStd) {
        Bitmap resultBitmap;
        if (practice.hanZiList.size() >= 9) {
            ArrayList<Bitmap> bitmaps = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                Bitmap bitmap;
                if (isStd) {
                    bitmap = BitmapFactory.decodeFile(practice.hanZiList.get(i).getPath());
                } else {
                    bitmap = BitmapFactory.decodeFile(practice.hanZiList.get(i).getWrittenPath());
                }
                bitmaps.add(bitmap);
            }
            int squareSize = bitmaps.get(0).getWidth();
            Bitmap drawnBitmap = Bitmap.createBitmap(squareSize * 3, squareSize * 3,
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(drawnBitmap);
            canvas.drawBitmap(bitmaps.get(0), 0, 0, null);
            canvas.drawBitmap(bitmaps.get(1), squareSize, 0, null);
            canvas.drawBitmap(bitmaps.get(2), squareSize * 2, 0, null);
            canvas.drawBitmap(bitmaps.get(3), 0, squareSize, null);
            canvas.drawBitmap(bitmaps.get(4), squareSize, squareSize, null);
            canvas.drawBitmap(bitmaps.get(5), squareSize * 2, squareSize, null);
            canvas.drawBitmap(bitmaps.get(6), 0, squareSize * 2, null);
            canvas.drawBitmap(bitmaps.get(7), squareSize, squareSize * 2, null);
            canvas.drawBitmap(bitmaps.get(8), squareSize * 2, squareSize * 2, null);
            resultBitmap = Bitmap.createScaledBitmap(drawnBitmap, squareSize, squareSize, true);
        } else if (practice.hanZiList.size() >= 4) {
            ArrayList<Bitmap> bitmaps = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Bitmap bitmap;
                if (isStd) {
                    bitmap = BitmapFactory.decodeFile(practice.hanZiList.get(i).getPath());
                } else {
                    bitmap = BitmapFactory.decodeFile(practice.hanZiList.get(i).getWrittenPath());
                }
                bitmaps.add(bitmap);
            }
            int squareSize = bitmaps.get(0).getWidth();
            Bitmap drawnBitmap = Bitmap.createBitmap(squareSize * 2, squareSize * 2,
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(drawnBitmap);
            canvas.drawBitmap(bitmaps.get(0), 0, 0, null);
            canvas.drawBitmap(bitmaps.get(1), squareSize, 0, null);
            canvas.drawBitmap(bitmaps.get(2), 0, squareSize, null);
            canvas.drawBitmap(bitmaps.get(3), squareSize, squareSize, null);
            resultBitmap = Bitmap.createScaledBitmap(drawnBitmap, squareSize, squareSize, true);
        } else if (!practice.hanZiList.isEmpty()) {
            if (isStd) {
                resultBitmap = BitmapFactory.decodeFile(practice.hanZiList.get(0).getPath());
            } else {
                resultBitmap = BitmapFactory.decodeFile(practice.hanZiList.get(0).getWrittenPath());
            }
        } else {
            resultBitmap = Bitmap.createBitmap(
                    Constants.IMAGE_SIZE, Constants.IMAGE_SIZE, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(resultBitmap);
            canvas.drawColor(Color.WHITE);
        }
        return resultBitmap;
    }
}
