package com.bupt.evaluate.extract;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

//轮廓提取器
public class ContourExtractor {

    //从图像中提取轮廓
    public static Contours mat2Contours(Mat img) {
        //提取轮廓
        List<MatOfPoint> originalData = new ArrayList<>();
        Imgproc.findContours(img, originalData, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        //轮廓近似
        List<MatOfPoint> cnts = approximate(originalData);
        //转换数据类型
        Contours contours = new Contours();
        for (MatOfPoint cnt : cnts) {
            PointList pointList = new PointList(cnt.toList());
            contours.add(pointList);
        }
        return contours;
    }

    //轮廓近似
    private static List<MatOfPoint> approximate(List<MatOfPoint> originalData) {
        List<MatOfPoint> contours = new ArrayList<>();
        for (MatOfPoint contour : originalData) {
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            Imgproc.approxPolyDP(contour2f, contour2f, 3, false);
            MatOfPoint matOfPoint = new MatOfPoint(contour2f.toArray());
            contours.add(matOfPoint);
        }
        return contours;
    }
}
