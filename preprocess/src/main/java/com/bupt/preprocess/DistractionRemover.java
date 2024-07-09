package com.bupt.preprocess;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

//图像干扰去除器
public class DistractionRemover {

    //去除图像干扰
    public static void removeDistraction(Mat inputMat, Mat stdMat) {
        Rect range = getRange(stdMat);
        fillWhite(inputMat, range);
    }

    //获取图像有效范围
    private static Rect getRange(Mat img) {
        //获取有效范围的左上角与右下角
        Point topLeft = new Point(img.cols(), img.rows());
        Point bottomRight = new Point(0, 0);
        for (int y = 0; y < img.rows(); y++) {
            for (int x = 0; x < img.cols(); x++) {
                double[] pixel = img.get(y, x);
                if (pixel[0] == 0) {
                    if (x < topLeft.x) {
                        topLeft.x = x;
                    }
                    if (y < topLeft.y) {
                        topLeft.y = y;
                    }
                    if (x > bottomRight.x) {
                        bottomRight.x = x;
                    }
                    if (y > bottomRight.y) {
                        bottomRight.y = y;
                    }
                }
            }
        }
        //扩大有效范围
        topLeft.x = Math.max(topLeft.x - Constants.RANGE_EXTEND, 0);
        topLeft.y = Math.max(topLeft.y - Constants.RANGE_EXTEND, 0);
        bottomRight.x = Math.min(bottomRight.x + Constants.RANGE_EXTEND, Constants.IMAGE_SIZE - 1);
        bottomRight.y = Math.min(bottomRight.y + Constants.RANGE_EXTEND, Constants.IMAGE_SIZE - 1);
        return new Rect(topLeft, bottomRight);
    }

    //将图像有效范围以外的部分填充为白色
    private static void fillWhite(Mat img, Rect range) {
        Mat mask = Mat.ones(img.size(), img.type());
        Imgproc.rectangle(mask, range.tl(), range.br(), new Scalar(255), Core.FILLED);
        Core.bitwise_not(mask, mask);
        img.setTo(new Scalar(255), mask);
    }
}
