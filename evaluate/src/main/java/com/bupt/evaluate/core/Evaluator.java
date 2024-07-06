package com.bupt.evaluate.core;

import android.graphics.Bitmap;

import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

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
        Strokes inputStrokes = Extractor.getStrokes(className, inputMat.clone());
        Strokes stdStrokes = Extractor.getStrokes(className, stdMat);
        //取得笔画评价数据
        Strokes.evaluateStrokes(inputStrokes, stdStrokes, inputMat);
        //设置汉字书写评价数据
        Evaluation evaluation = new Evaluation();
        setEvaluation(evaluation, inputStrokes, cnChar, inputBmp);
        //将输出图像替换为提取结果，用于测试
        //evaluation.outputBmp = Bitmap.createBitmap(stdMat.cols(), stdMat.rows(), Bitmap.Config.ARGB_8888);
        //Utils.matToBitmap(stdMat, evaluation.outputBmp);
        return evaluation;
    }

    //根据每个笔画的评价设置汉字书写评价数据
    private static void setEvaluation(Evaluation evaluation, Strokes strokes,
                                      String cnChar, Bitmap inputBmp) {
        int scoreSum = 0;
        int scoreNum = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (Stroke stroke : strokes) {
            if (stroke.evaluation.advice != null) {
                scoreSum += stroke.evaluation.score;
                stringBuilder.append(stroke.evaluation.advice);
                scoreNum += 1;
            }
        }
        if (scoreNum != 0) {
            //输出分数为平均分开根号乘10
            int scoreAverage = scoreSum / scoreNum;
            evaluation.score = (int) (Math.sqrt((double) scoreAverage) * 10);
        } else {
            evaluation.score = 100;
        }
        if (evaluation.score >= 90) {
            stringBuilder.append("这个“").append(cnChar).append("”字写得真好");
        } else if (evaluation.score >= 70) {
            stringBuilder.append("这个“").append(cnChar).append("”字写得不错");
        } else {
            stringBuilder.append("这个“").append(cnChar).append("”字需要加油");
        }
        evaluation.advice = stringBuilder.toString();
        Mat output = new Mat();
        if (!strokes.get(0).evaluation.outputMat.empty()) {
            output = strokes.get(0).evaluation.outputMat;
        } else {
            Utils.bitmapToMat(inputBmp, output, true);
        }
        Bitmap outputBmp = Bitmap.createBitmap(output.cols(), output.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(output, outputBmp);
        evaluation.outputBmp = outputBmp;
    }
}
