package com.bupt.evaluate.utils;

import android.util.Log;

import com.bupt.evaluate.Contours;
import com.bupt.evaluate.PointEx;
import com.bupt.evaluate.PointList;
import com.bupt.evaluate.Points;
import com.bupt.evaluate.Strokes;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.ximgproc.Ximgproc;

//图像处理器
public class ImgProcessor {

    //图像细化
    public static void thinning(Mat src, Mat dst) {
        OpenCVLoader.initDebug();
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_RGB2GRAY);//灰度化
        Imgproc.threshold(dst, dst, 127, 255, Imgproc.THRESH_BINARY_INV);//阈值
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(9, 9));
        Imgproc.morphologyEx(dst, dst, Imgproc.MORPH_CLOSE, kernel);//闭运算
        Ximgproc.thinning(dst, dst, Ximgproc.THINNING_ZHANGSUEN);//细化
    }

    //将Contours类绘制在Mat上
    public static void drawContours(Mat img, Contours contours) {
        Log.d("appTest", contours.toString());
        OpenCVLoader.initDebug();
        for (PointList contour : contours) {
            for (PointEx pointEx : contour) {
                Imgproc.circle(img, pointEx, 7, new Scalar(255, 255, 0), -1);
            }
        }
    }

    //将Points类绘制在Mat上
    public static void drawPoints(Mat img, Points points) {
        Log.d("appTest", points.toString());
        OpenCVLoader.initDebug();
        for (PointEx pointEx : points.get(Points.END)) {
            Imgproc.circle(img, pointEx, 5, new Scalar(0, 0, 255), -1);
        }
        for (PointEx pointEx : points.get(Points.INTER)) {
            Imgproc.circle(img, pointEx, 5, new Scalar(255, 0, 0), -1);
        }
    }

    //将Strokes类绘制在Mat上
    public static void drawStrokes(Mat img, Strokes strokes) {
        Log.d("appTest", strokes.toString());
        OpenCVLoader.initDebug();
        int strokeNum = strokes.size();
        int pointNum;
        for (int i = 0; i < strokeNum; i++) {
            pointNum = strokes.get(i).size();
            for (int j = 0; j < pointNum; j++) {
                PointEx pointEx = strokes.get(i).get(j);
                Imgproc.circle(img, pointEx, 3, new Scalar(0, 255, 0), -1);
                Imgproc.putText(img, " (" + i + "," + j + ")", pointEx,
                        0, 0.5, new Scalar(0, 255, 0), 1);
            }
        }
    }
}
