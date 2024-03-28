package com.bupt.evaluate.core;

import android.graphics.Bitmap;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.evaluate.StrokeEvaluation;
import com.bupt.evaluate.evaluate.StrokeEvaluator;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.ximgproc.Ximgproc;

import java.util.ArrayList;

//汉字书写评价器
public class Evaluator {

    //根据汉字名称、输入图像和标准图像取得评价
    public static Evaluation evaluate(String cnChar, Bitmap inputBmp, Bitmap stdBmp) {
        //加载OpenCV
        OpenCVLoader.initDebug();
        //转换图像格式
        Mat input = new Mat();
        Utils.bitmapToMat(inputBmp, input, true);
        Mat std = new Mat();
        Utils.bitmapToMat(stdBmp, std, true);
        //从图像中提取笔画
        Strokes inputStrokes = getStrokes(cnChar, input.clone());
        Strokes stdStrokes = getStrokes(cnChar, std);
        //取得笔画评价数据
        ArrayList<StrokeEvaluation> strokeEvaluations = evaluateStrokes(inputStrokes, stdStrokes, input);
        //设置汉字书写评价数据
        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluation(strokeEvaluations, cnChar, inputBmp, stdBmp);
        return evaluation;
    }

    //根据汉字名称和图像取得汉字笔画
    private static Strokes getStrokes(String cnChar, Mat img) {
        //预处理图像，细化为骨架
        preprocess(img);
        //从图像中提取特征点
        Points points = Points.mat2Points(img);
        //根据图像和特征点提取轮廓
        Contours contours = Contours.mat2Contours(img, points);
        //根据轮廓和特征点提取汉字笔画
        Strokes strokes = Strokes.extractStrokes(cnChar, contours, points);
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
        Imgproc.morphologyEx(img, img, Imgproc.MORPH_CLOSE, kernel);//闭运算
        Imgproc.morphologyEx(img, img, Imgproc.MORPH_OPEN, kernel);//开运算
        Ximgproc.thinning(img, img, Ximgproc.THINNING_ZHANGSUEN);//细化
    }

    //根据输入笔画和标准笔画取得笔画评价数据
    private static ArrayList<StrokeEvaluation> evaluateStrokes(
            Strokes inputStrokes, Strokes stdStrokes, Mat img) {
        Imgproc.cvtColor(img, img, Imgproc.COLOR_RGBA2RGB);//转换图像通道，避免绘制时颜色异常
        ArrayList<StrokeEvaluation> strokeEvaluations = new ArrayList<>();
        int strokeIndex = 0;
        for (int i = 0; i < stdStrokes.size(); i++) {
            StrokeEvaluation strokeEvaluation = StrokeEvaluator.evaluateStroke(
                    inputStrokes.get(i), stdStrokes.get(i), img, strokeIndex);
            strokeEvaluations.add(strokeEvaluation);
            if (!strokeEvaluation.advice.isEmpty()) {
                strokeIndex += 1;
            }
        }
        return strokeEvaluations;
    }
}
