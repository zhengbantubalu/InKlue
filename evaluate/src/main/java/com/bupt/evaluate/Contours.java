package com.bupt.evaluate;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//轮廓，其中包含OpenCV的轮廓数据
public class Contours extends ArrayList<PointList> {
    public List<MatOfPoint2f> contours2f;//拟合后的轮廓
    public Mat hierarchy;//轮廓层级

    //从图像中提取轮廓
    public Contours(Mat img) {
        hierarchy = new Mat();
        OpenCVLoader.initDebug();
        //提取轮廓
        List<MatOfPoint> originalData = new ArrayList<>();
        Imgproc.findContours(img, originalData, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        //轮廓拟合
        contours2f = matOfPoint2matOfPoint2f(originalData);
        for (MatOfPoint2f contour2f : contours2f) {
            Imgproc.approxPolyDP(contour2f, contour2f, 10, true);
            PointList contour = new PointList(contour2f.toList());
            contour.dropDuplicates(10);
            this.add(contour);
        }
    }

    //List<MatOfPoint>转List<MatOfPoint2f>
    private List<MatOfPoint2f> matOfPoint2matOfPoint2f(List<MatOfPoint> originalData) {
        List<MatOfPoint2f> contours2f = new ArrayList<>();
        for (MatOfPoint contour : originalData) {
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            contours2f.add(contour2f);
        }
        return contours2f;
    }
}
