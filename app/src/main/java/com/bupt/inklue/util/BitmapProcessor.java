package com.bupt.inklue.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.bupt.inklue.data.CardsData;

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

    //图像预处理，包含裁剪、旋转、缩放、阈值，将拍摄的图片处理为评价模块可接收的图片，并覆盖原图
    public static void preprocess(String filePath, int squareSize) {
        Bitmap originalBmp = BitmapFactory.decodeFile(filePath);
        Bitmap scaledBmp = Bitmap.createScaledBitmap(originalBmp,
                squareSize * originalBmp.getWidth() / originalBmp.getHeight()
                , squareSize, true);
        Bitmap croppedBmp = Bitmap.createBitmap(scaledBmp,
                (scaledBmp.getWidth() - squareSize) / 2, 0, squareSize, squareSize);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedBmp = Bitmap.createBitmap(croppedBmp, 0, 0,
                squareSize, squareSize, matrix, false);
        Bitmap thresholdBmp = threshold(rotatedBmp);
        save(thresholdBmp, filePath);
    }

    //图像阈值处理
    private static Bitmap threshold(Bitmap bitmap) {
        Mat mat = new Mat();
        Utils.bitmapToMat(bitmap, mat);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY);
        Imgproc.threshold(mat, mat, 100, 255, Imgproc.THRESH_BINARY);
        Utils.matToBitmap(mat, bitmap);
        return bitmap;
    }

    //根据卡片数据列表创建练习封面，并保存到指定路径
    public static void createCover(CardsData cardsData, String filePath) {
        if (cardsData.size() >= 9) {
            ArrayList<Bitmap> bitmaps = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                Bitmap bitmap = BitmapFactory.decodeFile(cardsData.get(i).getStdImgPath());
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
        } else if (cardsData.size() >= 4) {
            ArrayList<Bitmap> bitmaps = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Bitmap bitmap = BitmapFactory.decodeFile(cardsData.get(i).getStdImgPath());
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
        } else if (!cardsData.isEmpty()) {
            Bitmap bitmap = BitmapFactory.decodeFile(cardsData.get(0).getStdImgPath());
            save(bitmap, filePath);
        }
    }
}
