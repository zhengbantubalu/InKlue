package com.bupt.evaluate;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
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

    //从图像中提取轮廓
    public Contours(Mat img) {
        OpenCVLoader.initDebug();
        //提取轮廓
        List<MatOfPoint> originalData = new ArrayList<>();
        Imgproc.findContours(img, originalData, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        //轮廓近似
        List<MatOfPoint> contours = approxContours(originalData);
        //转换数据类型
        for (MatOfPoint contour : contours) {
            PointList pointList = new PointList(contour.toList());
            this.add(pointList);
        }
        //分割轮廓
        splitContours();
    }

    //根据起止点查找匹配轮廓，返回一个匹配的轮廓，并正序排列，无匹配则返回空
    public PointList findMatchContour(PointEx start, PointEx end) {
        int matchCode;
        for (PointList contour : this) {
            matchCode = contour.match(start, end, 10);
            if (matchCode == 1) {
                return contour;
            } else if (matchCode == 2) {
                Collections.reverse(contour);
                return contour;
            }
        }
        return null;
    }

    //分割轮廓
    //把轮廓从拐点断开，分割成两段轮廓
    //保留原始轮廓，所有分割出的轮廓加在原始轮廓后面
    private void splitContours() {
        int contourNum = this.size();
        for (int i = 0; i < contourNum; i++) {
            PointList contour = this.get(i);
            for (int j = 1, previous = 0; j < contour.size() - 1; j++) {
                int angle = contour.getAngle(j);
                if (angle < 110 || angle > 250) {
                    PointList sublist = new PointList(contour.subList(previous, j + 1));
                    this.add(sublist);
                    previous = j;
                }
            }
        }
    }

    //轮廓近似
    private List<MatOfPoint> approxContours(List<MatOfPoint> originalData) {
        List<MatOfPoint> contours = new ArrayList<>();
        for (MatOfPoint contour : originalData) {
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            Imgproc.approxPolyDP(contour2f, contour2f, 10, false);
            MatOfPoint matOfPoint = new MatOfPoint(contour2f.toArray());
            contours.add(matOfPoint);
        }
        return contours;
    }
}
