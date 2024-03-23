package com.bupt.evaluate.processor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Strokes;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.ximgproc.Ximgproc;

import java.util.Objects;

//图像处理器
public class ImageProcessor {

    //图像细化
    public static void thinning(Mat src, Mat dst) {
        OpenCVLoader.initDebug();
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_RGB2GRAY);//灰度化
        Imgproc.threshold(dst, dst, 127, 255, Imgproc.THRESH_BINARY_INV);//阈值
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(9, 9));
        Imgproc.morphologyEx(dst, dst, Imgproc.MORPH_CLOSE, kernel);//闭运算
        Ximgproc.thinning(dst, dst, Ximgproc.THINNING_ZHANGSUEN);//细化
    }

    //绘制轮廓
    public static void drawContours(Mat img, Contours contours) {
        OpenCVLoader.initDebug();
        int contourNum = contours.size();
        int pointNum;
        for (int i = 0; i < contourNum; i++) {
            PointList contour = contours.get(i);
            pointNum = contour.size();
            for (int j = 0; j < pointNum; j++) {
                PointEx pointEx = contour.get(j);
                Imgproc.circle(img, pointEx, 5, new Scalar(255, 255, 0), -1);
                PointEx temp = new PointEx(pointEx);
                temp.y += 20;
                Imgproc.putText(img, " (" + i + "," + j + ")", temp,
                        0, 0.5, new Scalar(255, 255, 0), 1);
            }
        }
    }

    //绘制特征点
    public static void drawPoints(Mat img, Points points) {
        OpenCVLoader.initDebug();
        for (PointEx pointEx : Objects.requireNonNull(points.get(Points.END))) {
            Imgproc.circle(img, pointEx, 5, new Scalar(0, 0, 255), -1);
        }
        for (PointEx pointEx : Objects.requireNonNull(points.get(Points.INTER))) {
            Imgproc.circle(img, pointEx, 5, new Scalar(255, 0, 0), -1);
        }
    }

    //绘制笔画
    public static void drawStrokes(Mat img, Strokes strokes) {
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
