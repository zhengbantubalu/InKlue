package com.bupt.evaluate;

import android.graphics.Bitmap;
import android.util.Log;

import com.bupt.evaluate.utils.ImgProcessor;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

//对一个汉字的评价
public class Evaluation {
    public int score;//分数
    public StringBuilder advice;//建议
    public Bitmap outputImg;//输出图片

    //根据汉字名称和汉字图像取得评价
    public Evaluation(String cnChar, Bitmap inputImg) {
        this.score = 0;
        this.advice = new StringBuilder();
        this.outputImg = null;
        OpenCVLoader.initDebug();
        Mat img = new Mat();
        Utils.bitmapToMat(inputImg, img, true);
        ImgProcessor.thinning(img, img);//图像预处理细化为骨架
        Points points = new Points(img);//从图像中提取点集
        Log.d("appTest", points.toString());
        Strokes strokes = new Strokes(cnChar, points);//从点集中提取笔画
        Log.d("appTest", strokes.toString());
        Imgproc.cvtColor(img, img, Imgproc.COLOR_GRAY2RGB);
        ImgProcessor.drawPoints(img, points);
        ImgProcessor.drawStrokes(img, strokes);
        this.score = 100;
        this.advice.append("这个\"" + cnChar + "\"字写得太棒了");
        Bitmap outputImg = Bitmap.createBitmap(img.cols(), img.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img, outputImg);
        this.outputImg = outputImg;
    }
}
