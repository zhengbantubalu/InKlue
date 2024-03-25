package com.bupt.evaluate.util;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;

import org.opencv.core.Mat;
import org.opencv.core.Point;
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
    public static void drawLine(Mat img, Mat line) {
        double vx = line.get(0, 0)[0];
        double vy = line.get(1, 0)[0];
        double x0 = line.get(2, 0)[0];
        double y0 = line.get(3, 0)[0];
        Point pt1 = new Point(x0 - 1000 * vx, y0 - 1000 * vy);
        Point pt2 = new Point(x0 + 1000 * vx, y0 + 1000 * vy);
        Imgproc.line(img, pt1, pt2, new Scalar(255, 0, 0), 1);
    }
}
