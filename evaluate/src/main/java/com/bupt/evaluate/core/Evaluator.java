package com.bupt.evaluate.core;

import android.graphics.Bitmap;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Strokes;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.ximgproc.Ximgproc;

//汉字书写评价器
public class Evaluator {

    //根据汉字名称、输入图像和标准图像取得评价
    public static Evaluation evaluate(String cnChar, String className, Bitmap inputBmp, Bitmap stdBmp) {
        //加载OpenCV
        System.loadLibrary("opencv_java3");
        //转换图像格式
        Mat inputMat = new Mat();
        Utils.bitmapToMat(inputBmp, inputMat, true);
        Mat stdMat = new Mat();
        Utils.bitmapToMat(stdBmp, stdMat, true);
        //从图像中提取笔画
        Strokes inputStrokes = getStrokes(className, inputMat.clone());
        Strokes stdStrokes = getStrokes(className, stdMat);
        //取得笔画评价数据
        Strokes.evaluateStrokes(inputStrokes, stdStrokes, inputMat);
        //设置汉字书写评价数据
        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluation(inputStrokes, cnChar, inputBmp, stdBmp);
        //将输出图像替换为提取结果，用于测试
//        evaluation.outputBmp = Bitmap.createBitmap(stdMat.cols(), stdMat.rows(), Bitmap.Config.ARGB_8888);
//        Utils.matToBitmap(stdMat, evaluation.outputBmp);
        return evaluation;
    }

    //根据汉字名称和图像取得汉字笔画
    private static Strokes getStrokes(String className, Mat img) {
        //预处理图像，细化为骨架
        preprocess(img);
        //从图像中提取特征点
        Points points = Points.mat2Points(img);
        //根据图像和特征点提取轮廓
        Contours contours = Contours.mat2Contours(img, points);
        //根据轮廓和特征点提取汉字笔画
        Strokes strokes = Strokes.extractStrokes(className, contours, points);
        //绘制提取结果，用于测试
//        Imgproc.cvtColor(img, img, Imgproc.COLOR_GRAY2RGB);
//        ImageDrawer.drawPoints(img, points);
//        ImageDrawer.drawContours(img, contours);
//        ImageDrawer.drawStrokes(img, strokes);
        return strokes;
    }

    //预处理图像，细化为骨架，输出图像为单通道Mat
    private static void preprocess(Mat img) {
        Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2GRAY);//灰度化
        Imgproc.threshold(img, img, 127, 255, Imgproc.THRESH_BINARY_INV);//阈值
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(9, 9));
        Imgproc.morphologyEx(img, img, Imgproc.MORPH_OPEN, kernel);//开运算
        Imgproc.morphologyEx(img, img, Imgproc.MORPH_CLOSE, kernel);//闭运算
        Ximgproc.thinning(img, img, Ximgproc.THINNING_ZHANGSUEN);//细化
    }
}
