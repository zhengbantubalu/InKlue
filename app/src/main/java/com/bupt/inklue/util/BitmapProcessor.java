package com.bupt.inklue.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.bupt.inklue.data.PracticeData;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Bitmap图像处理器
public class BitmapProcessor {

    //保存Bitmap
    public static void save(Bitmap bitmap, String filePath) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (IOException ignored) {
        }
    }

    //将黑白图像变为指定颜色的半透明图像
    public static Bitmap toTransparent(String filePath, Scalar color) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        Mat alpha = new Mat();
        Utils.bitmapToMat(bitmap, alpha);
        Imgproc.cvtColor(alpha, alpha, Imgproc.COLOR_RGB2GRAY);
        Imgproc.threshold(alpha, alpha, 127, 127, Imgproc.THRESH_BINARY_INV);
        List<Mat> channels = new ArrayList<>();
        channels.add(new Mat(alpha.size(), CvType.CV_8UC3, color));
        channels.add(alpha);
        Mat rgba = new Mat();
        Core.merge(channels, rgba);
        Utils.matToBitmap(rgba, bitmap);
        return bitmap;
    }

    //创建练习封面，并保存到指定路径
    //isStd为真则以标准图像创建，否则以书写图像创建
    public static void createCover(PracticeData practiceData, String filePath, boolean isStd) {
        if (practiceData.charsData.size() >= 9) {
            ArrayList<Bitmap> bitmaps = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                Bitmap bitmap;
                if (isStd) {
                    bitmap = BitmapFactory.decodeFile(practiceData.charsData.get(i).getStdImgPath());
                } else {
                    bitmap = BitmapFactory.decodeFile(practiceData.charsData.get(i).getWrittenImgPath());
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
            Bitmap resultBitmap = Bitmap.createScaledBitmap(drawnBitmap, squareSize, squareSize, true);
            save(resultBitmap, filePath);
        } else if (practiceData.charsData.size() >= 4) {
            ArrayList<Bitmap> bitmaps = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Bitmap bitmap;
                if (isStd) {
                    bitmap = BitmapFactory.decodeFile(practiceData.charsData.get(i).getStdImgPath());
                } else {
                    bitmap = BitmapFactory.decodeFile(practiceData.charsData.get(i).getWrittenImgPath());
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
            Bitmap resultBitmap = Bitmap.createScaledBitmap(drawnBitmap, squareSize, squareSize, true);
            save(resultBitmap, filePath);
        } else if (!practiceData.charsData.isEmpty()) {
            Bitmap bitmap;
            if (isStd) {
                bitmap = BitmapFactory.decodeFile(practiceData.charsData.get(0).getStdImgPath());
            } else {
                bitmap = BitmapFactory.decodeFile(practiceData.charsData.get(0).getWrittenImgPath());
            }
            save(bitmap, filePath);
        }
    }
}
