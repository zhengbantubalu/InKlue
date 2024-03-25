package com.bupt.evaluate.util;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.Line;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.Objects;

//图像绘制器
public class ImageDrawer {

    //绘制轮廓
    public static void drawContours(Mat img, Contours contours) {
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
        for (PointEx pointEx : Objects.requireNonNull(points.get(Points.END))) {
            Imgproc.circle(img, pointEx, 5, new Scalar(0, 0, 255), -1);
        }
        for (PointEx pointEx : Objects.requireNonNull(points.get(Points.INTER))) {
            Imgproc.circle(img, pointEx, 5, new Scalar(255, 0, 0), -1);
        }
    }

    //绘制笔画
    public static void drawStrokes(Mat img, Strokes strokes) {
        int strokeNum = strokes.size();
        int pointNum;
        for (int i = 0; i < strokeNum; i++) {
            Stroke stroke = strokes.get(i);
            pointNum = stroke.size();
            for (int j = 0; j < pointNum; j++) {
                PointEx pointEx = stroke.get(j);
                Imgproc.circle(img, pointEx, 3, new Scalar(0, 255, 0), -1);
                Imgproc.putText(img, " (" + i + "," + j + ")", pointEx,
                        0, 0.5, new Scalar(0, 255, 0), 1);
            }
        }
    }

    //绘制直线
    public static void drawLine(Mat img, Line line) {
        Imgproc.line(img, line.p1, line.p2, new Scalar(255, 0, 0), 1);
    }
}
