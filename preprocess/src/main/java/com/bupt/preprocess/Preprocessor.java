package com.bupt.preprocess;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

//汉字图像预处理器
public class Preprocessor {

    //图像预处理，将拍摄的图片处理为评价模块可接收的图片
    public static Bitmap preprocess(Bitmap inputBmp, Bitmap stdBmp) {
        //转换图像格式
        Mat inputMat = new Mat();
        Utils.bitmapToMat(inputBmp, inputMat, true);
        Mat stdMat = new Mat();
        Utils.bitmapToMat(stdBmp, stdMat, true);
        //预处理的准备工作
        PrePreprocessor.prePreprocess(inputMat, stdMat);
        //去除图像干扰
        DistractionRemover.removeDistraction(inputMat, stdMat);
        //转换图像格式
        Bitmap outputBmp = Bitmap.createBitmap(
                inputMat.cols(), inputMat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(inputMat, outputBmp);
        return outputBmp;
    }
}
