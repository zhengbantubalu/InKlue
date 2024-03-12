package com.bupt.evaluate;

import android.graphics.Bitmap;

import com.bupt.evaluate.utils.ImgProcessor;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

//对一个汉字的评价
public class Evaluation {
    public int score;//分数
    public StringBuilder advice;//建议
    public Bitmap outputBmp;//输出图片

    //根据汉字名称、输入图像和标准图像取得评价
    public Evaluation(String cnChar, Bitmap inputBmp, Bitmap stdBmp) {
        //初始化评价数据
        score = 0;
        advice = new StringBuilder();
        outputBmp = null;
        //OpenCV预处理图像，细化为骨架
        OpenCVLoader.initDebug();
        Mat input = new Mat();
        Utils.bitmapToMat(inputBmp, input, true);
        Mat std = new Mat();
        Utils.bitmapToMat(stdBmp, std, true);
        ImgProcessor.thinning(input, input);
        ImgProcessor.thinning(std, std);
        //从骨架图像中提取信息
        Contours inputContours = new Contours(input);//从输入图像中提取轮廓
        Points inputPoints = new Points(input);//从输入图像中提取点集
        Strokes inputStrokes = new Strokes(cnChar, inputContours, inputPoints);//从轮廓和点集中提取笔画
        Contours stdContours = new Contours(std);//从标准图像中提取轮廓
        //绘制提取结果
        Imgproc.cvtColor(input, input, Imgproc.COLOR_GRAY2RGB);
        ImgProcessor.drawContours(input, inputContours);
        ImgProcessor.drawPoints(input, inputPoints);
        ImgProcessor.drawStrokes(input, inputStrokes);
        //设置评价数据
        score = 100 - (int) Imgproc.matchShapes(
                inputContours.contours2f.get(0), stdContours.contours2f.get(0),
                Imgproc.CONTOURS_MATCH_I2, 0);
        advice.append("这个\"").append(cnChar).append("\"字写得太棒了");
        Bitmap outputImg = Bitmap.createBitmap(input.cols(), input.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(input, outputImg);
        this.outputBmp = outputImg;
    }
}
