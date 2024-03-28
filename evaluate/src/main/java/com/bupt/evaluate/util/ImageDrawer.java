package com.bupt.evaluate.util;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.Curve;
import com.bupt.evaluate.data.Line;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

//图像绘制器
public class ImageDrawer {

    //绘制直线
    public static void drawLine(Mat img, Line line, Scalar color, int thickness) {
        Imgproc.line(img, line.p1, line.p2, color, thickness);
    }

    //绘制曲线
    public static void drawCurve(Mat img, Curve curve, Scalar color, int thickness) {
        for (int i = 0; i < curve.size() - 1; i++) {
            Imgproc.line(img, curve.get(i), curve.get(i + 1), color, thickness);
        }
    }

    //绘制文本
    public static void drawText(Mat img, String text, PointEx position, Scalar color) {
        PointEx p = new PointEx((int) position.x - 10, (int) position.y + 10);
        Imgproc.putText(img, text, p, 0, 1, color, 2);
    }

    //绘制笔画
    public static void drawStroke(Mat img, Stroke stroke,
                                  Scalar strokeColor, Scalar textColor, int strokeIndex) {
        if (stroke.isStraight) {
            ImageDrawer.drawLine(img, stroke.line, strokeColor, Constants.THICKNESS);
        } else {
            ImageDrawer.drawCurve(img, stroke.curve, strokeColor, Constants.THICKNESS);
        }
        ImageDrawer.drawText(img, Integer.toString(strokeIndex + 1),
                stroke.getQuartPoint(), textColor);
    }

    //绘制特征点，用于测试
    public static void drawPoints(Mat img, Points points) {
        for (int i = 0; i < points.end.size(); i++) {
            Imgproc.circle(img, points.end.get(i), 5, new Scalar(Constants.COLOR_BLUE), -1);
            PointEx p = new PointEx((int) points.end.get(i).x - 20, (int) points.end.get(i).y);
            Imgproc.putText(img, i + "", p,
                    0, 0.7, new Scalar(Constants.COLOR_BLUE), 1);
        }
        for (int i = 0; i < points.inter.size(); i++) {
            Imgproc.circle(img, points.inter.get(i), 5, new Scalar(Constants.COLOR_RED), -1);
            PointEx p = new PointEx((int) points.inter.get(i).x - 20, (int) points.inter.get(i).y);
            Imgproc.putText(img, i + "", p,
                    0, 0.7, new Scalar(Constants.COLOR_RED), 1);
        }
    }

    //绘制轮廓，用于测试
    public static void drawContours(Mat img, Contours contours) {
        for (int i = 0; i < contours.size(); i++) {
            for (int j = 0; j < contours.get(i).size(); j++) {
                Imgproc.circle(img, contours.get(i).get(j), 3,
                        new Scalar(Constants.COLOR_YELLOW), -1);
            }
        }
    }

    //绘制笔画，用于测试
    public static void drawStrokes(Mat img, Strokes strokes) {
        for (int i = 0; i < strokes.size(); i++) {
            for (int j = 0; j < strokes.get(i).size(); j++) {
                Imgproc.circle(img, strokes.get(i).get(j), 3,
                        new Scalar(Constants.COLOR_GREEN), -1);
                Imgproc.putText(img, " (" + i + "," + j + ")", strokes.get(i).get(j),
                        0, 0.5, new Scalar(Constants.COLOR_GREEN), 1);
            }
        }
    }
}
