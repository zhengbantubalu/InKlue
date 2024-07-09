package com.bupt.preprocess;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

//预处理的预处理器
public class PrePreprocessor {

    //预处理的准备工作
    public static void prePreprocess(Mat inputMat, Mat stdMat) {
        //灰度化
        gray(inputMat);
        gray(stdMat);
        //旋转
        rotate(inputMat);
        //缩放
        resize(inputMat);
        //裁剪
        crop(inputMat);
        //阈值
        threshold(inputMat);
        threshold(stdMat);
    }

    //灰度化
    private static void gray(Mat img) {
        Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2GRAY);
    }

    //旋转
    private static void rotate(Mat img) {
        if (img.width() > img.height()) {
            Core.rotate(img, img, Core.ROTATE_90_CLOCKWISE);
        }
    }

    //缩放
    private static void resize(Mat img) {
        Size size = new Size();
        size.width = Constants.IMAGE_SIZE;
        size.height = (double) (Constants.IMAGE_SIZE * img.height() / img.width());
        Imgproc.resize(img, img, size);
    }

    //裁剪
    private static void crop(Mat img) {
        Rect roi = new Rect(0, (img.height() - Constants.IMAGE_SIZE) / 2,
                Constants.IMAGE_SIZE, Constants.IMAGE_SIZE);
        Mat croppedImg = new Mat(img, roi);
        croppedImg.copyTo(img);
    }

    //阈值
    private static void threshold(Mat img) {
        Imgproc.threshold(img, img, 63, 255, Imgproc.THRESH_BINARY);
    }
}
