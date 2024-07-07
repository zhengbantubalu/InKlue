package com.bupt.evaluate.extract;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.util.Constants;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

//轮廓提取器
public class ContourExtractor {

    //从图像中提取轮廓
    public static Contours mat2Contours(Mat img, Points points) {
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
        //重置笔画端点坐标为Points中的端点坐标，防止轮廓近似导致笔画变短
        resetEndPoints(contours, points);
        //重置笔画交点坐标为Points中的交点坐标，防止交点消歧导致找不到交点
        resetInterPoints(contours, points);
        return contours;
    }

    //轮廓近似
    private static List<MatOfPoint> approximate(List<MatOfPoint> originalData) {
        List<MatOfPoint> contours = new ArrayList<>();
        for (MatOfPoint contour : originalData) {
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            Imgproc.approxPolyDP(contour2f, contour2f, 3, true);
            MatOfPoint matOfPoint = new MatOfPoint(contour2f.toArray());
            contours.add(matOfPoint);
        }
        return contours;
    }

    //将笔画端点重置为特征点中的端点
    private static void resetEndPoints(Contours contours, Points points) {
        for (PointList contour : contours) {
            for (int i = 0; i < contour.size(); i++) {
                int angle = contour.getAngle(i);
                //角度接近0或360，说明该轮廓点是笔画端点
                if (angle < Constants.MAX_ANGLE || angle > 360 - Constants.MAX_ANGLE) {
                    contour.set(i, points.end.getNearestPoint(contour.get(i)));
                }
            }
        }
    }

    //将笔画交点重置为特征点中的交点
    private static void resetInterPoints(Contours contours, Points points) {
        for (PointEx interPoint : points.inter) {
            if (!contours.has(interPoint, Constants.MAX_DISTANCE)) {
                //将所有在标准距离2倍以内的轮廓点都重置为特征点中的交点
                ArrayList<int[]> indexes = contours.getNearPointIndexes(
                        interPoint, Constants.MAX_DISTANCE * 2);
                for (int[] index : indexes) {
                    contours.get(index[0]).set(index[1], interPoint);
                }
            }
        }
    }
}
