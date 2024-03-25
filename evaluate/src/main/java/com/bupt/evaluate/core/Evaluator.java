package com.bupt.evaluate.core;

import android.graphics.Bitmap;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.ContourExtractor;
import com.bupt.evaluate.extract.PointExtractor;
import com.bupt.evaluate.extract.StrokeExtractor;
import com.bupt.evaluate.score.StrokeEvaluation;
import com.bupt.evaluate.score.StrokeEvaluator;
import com.bupt.evaluate.util.ImageDrawer;

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
        //预处理图像，细化为骨架
        Mat input = preprocess(inputBmp);
        Mat std = preprocess(stdBmp);
        //从图像中提取笔画
        Strokes inputStrokes = getStrokes(cnChar, input);
        Strokes stdStrokes = getStrokes(cnChar, std);
        //取得笔画评价数据
        ArrayList<StrokeEvaluation> strokeEvaluations = evaluateStrokes(inputStrokes, stdStrokes);
        //设置汉字书写评价数据
        return setEvaluation(cnChar, strokeEvaluations, inputBmp, stdBmp);
    }

    //预处理图像，细化为骨架，输出图像为单通道Mat
    private static Mat preprocess(Bitmap bmp) {
        Mat mat = new Mat();
        Utils.bitmapToMat(bmp, mat, true);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY);//灰度化
        Imgproc.threshold(mat, mat, 127, 255, Imgproc.THRESH_BINARY_INV);//阈值
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(9, 9));
        Imgproc.morphologyEx(mat, mat, Imgproc.MORPH_CLOSE, kernel);//闭运算
        Ximgproc.thinning(mat, mat, Ximgproc.THINNING_ZHANGSUEN);//细化
        return mat;
    }

    //根据汉字名称和图像取得汉字笔画
    private static Strokes getStrokes(String cnChar, Mat img) {
        //从输入图像中提取轮廓
        Contours contours = ContourExtractor.mat2Contours(img);
        //从输入图像中提取点集
        Points points = PointExtractor.mat2Points(img);
        //根据轮廓和特征点提取汉字笔画
        return StrokeExtractor.extractStrokes(cnChar, contours, points);
    }

    //根据输入笔画和标准笔画取得笔画评价数据
    private static ArrayList<StrokeEvaluation> evaluateStrokes(Strokes inputStrokes, Strokes stdStrokes) {
        ArrayList<StrokeEvaluation> strokeEvaluations = new ArrayList<>();
        for (int i = 0; i < stdStrokes.size(); i++) {
            strokeEvaluations.add(
                    StrokeEvaluator.evaluateStroke(inputStrokes.get(i), stdStrokes.get(i)));
        }
        return strokeEvaluations;
    }

    //根据每个笔画的评价设置汉字书写评价数据
    private static Evaluation setEvaluation(String cnChar, ArrayList<StrokeEvaluation> strokeEvaluations,
                                            Bitmap inputBmp, Bitmap stdBmp) {
        Evaluation evaluation = new Evaluation();
        int scoreSum = 0;
        for (StrokeEvaluation strokeEvaluation : strokeEvaluations) {
            scoreSum += strokeEvaluation.score;
        }
        evaluation.score = scoreSum / strokeEvaluations.size();
        if (evaluation.score > 80) {
            evaluation.advice = "这个\"" + cnChar + "\"字写得真直";
        } else if (evaluation.score > 60) {
            evaluation.advice = "这个\"" + cnChar + "\"字写得有点弯";
        } else {
            evaluation.advice = "这个\"" + cnChar + "\"字写得曲里拐弯的";
        }
        Mat output = preprocess(inputBmp);
        Strokes inputStrokes = getStrokes(cnChar, output);
        Imgproc.cvtColor(output, output, Imgproc.COLOR_GRAY2RGB);
        ImageDrawer.drawStrokes(output, inputStrokes);
        for (Stroke stroke : inputStrokes) {
            stroke.drawFitLine(output);
        }
        Bitmap outputBmp = Bitmap.createBitmap(output.cols(), output.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(output, outputBmp);
        evaluation.outputBmp = outputBmp;
        return evaluation;
    }
}
