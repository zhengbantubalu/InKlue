package com.bupt.evaluate.core;

import android.graphics.Bitmap;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.processor.ContourExtractor;
import com.bupt.evaluate.processor.ImageProcessor;
import com.bupt.evaluate.processor.PointExtractor;
import com.bupt.evaluate.processor.StrokeExtractor;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

//汉字书写评价器
public class Evaluator {

    //根据汉字名称、输入图像和标准图像取得评价
    public static Evaluation evaluate(String cnChar, Bitmap inputBmp, Bitmap stdBmp) {
        //OpenCV预处理图像，细化为骨架
        OpenCVLoader.initDebug();
        Mat input = new Mat();
        Utils.bitmapToMat(inputBmp, input, true);
//        Mat std = new Mat();
//        Utils.bitmapToMat(stdBmp, std, true);
        ImageProcessor.thinning(input, input);
//        ImageProcessor.thinning(std, std);
        //从骨架图像中提取信息
        Contours inputContours = ContourExtractor.mat2Contours(input);//从输入图像中提取轮廓
        Points inputPoints = PointExtractor.mat2Points(input);//从输入图像中提取点集
        inputContours.split();//分割轮廓(旧方法)
        Strokes inputStrokes = new Strokes(cnChar, inputContours, inputPoints);//从轮廓和点集中提取笔画(旧方法)
//        Contours stdContours = ContourExtractor.mat2Contours(std);//从标准图像中提取轮廓
        //绘制提取结果
        Imgproc.cvtColor(input, input, Imgproc.COLOR_GRAY2RGB);
        ImageProcessor.drawContours(input, inputContours);
        ImageProcessor.drawPoints(input, inputPoints);
        ImageProcessor.drawStrokes(input, inputStrokes);
        //设置评价数据
        Evaluation evaluation = new Evaluation();
        evaluation.score = 100;
        evaluation.advice = "这个\"" + cnChar + "\"字写得太棒了";
        Bitmap outputImg = Bitmap.createBitmap(input.cols(), input.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(input, outputImg);
        evaluation.outputBmp = outputImg;
        return evaluation;
    }
}
