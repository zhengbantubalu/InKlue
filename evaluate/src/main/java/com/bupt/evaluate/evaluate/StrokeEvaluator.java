package com.bupt.evaluate.evaluate;

import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.util.Constants;
import com.bupt.evaluate.util.ImageDrawer;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;

//笔画书写评价器，用于评价一个笔画的书写
public class StrokeEvaluator {

    //取得对一个笔画的评价
    public static StrokeEvaluation evaluateStroke(
            Stroke inputStroke, Stroke stdStroke, Mat img, int strokeIndex) {
        //如果输入笔画为空，则反馈错误信息
        if (inputStroke.isEmpty()) {
            if (stdStroke.isStraight) {
                stdStroke.fitLine();
            } else {
                stdStroke.interpolateCurve(stdStroke.getLength() / Constants.STEP_SIZE + 1);
            }
            return emptyError(stdStroke, img, strokeIndex);
        }
        EvaluationBuilder evaluationBuilder;
        //根据笔画是否是直线，采用不同的评价方法取得评价构建器
        if (inputStroke.isStraight) {
            evaluationBuilder = evaluateLine(inputStroke, stdStroke, img, strokeIndex);
        } else {
            evaluationBuilder = evaluateCurve(inputStroke, stdStroke, img, strokeIndex);
        }
        //将构建器转换成评价
        return evaluationBuilder.toStrokeEvaluation(strokeIndex);
    }

    //评价直线笔画
    private static EvaluationBuilder evaluateLine(
            Stroke inputStroke, Stroke stdStroke, Mat img, int strokeIndex) {
        //创建笔画评价构建器
        EvaluationBuilder evaluationBuilder = new EvaluationBuilder(img);
        //直线拟合
        inputStroke.fitLine();
        stdStroke.fitLine();
        //评价平直度
        evaluationBuilder.evaluateFeature(inputStroke, stdStroke, EvaluationBuilder.LINEARITY, strokeIndex);
        //评价长度
        evaluationBuilder.evaluateFeature(inputStroke, stdStroke, EvaluationBuilder.LENGTH, strokeIndex);
        //评价倾角
        evaluationBuilder.evaluateFeature(inputStroke, stdStroke, EvaluationBuilder.ANGLE, strokeIndex);
        //评价位置
        evaluationBuilder.evaluateFeature(inputStroke, stdStroke, EvaluationBuilder.POSITION, strokeIndex);
        return evaluationBuilder;
    }

    //评价曲线笔画
    private static EvaluationBuilder evaluateCurve(
            Stroke inputStroke, Stroke stdStroke, Mat img, int strokeIndex) {
        //创建笔画评价构建器
        EvaluationBuilder evaluationBuilder = new EvaluationBuilder(img);
        //曲线插值
        int size = stdStroke.getLength() / Constants.STEP_SIZE + 1;//+1防止提取笔画失败导致size为0
        inputStroke.interpolateCurve(size);
        stdStroke.interpolateCurve(size);
        //评价相似度
        evaluationBuilder.evaluateFeature(inputStroke, stdStroke, EvaluationBuilder.SIMILARITY, strokeIndex);
        return evaluationBuilder;
    }

    //输入笔画为空，反馈错误信息
    public static StrokeEvaluation emptyError(Stroke stroke, Mat img, int strokeIndex) {
        StrokeEvaluation strokeEvaluation = new StrokeEvaluation();
        strokeEvaluation.score = 0;
        strokeEvaluation.advice = "第" + (strokeIndex + 1) + "笔未识成功，请规范书写\n";
        ImageDrawer.drawStroke(img, stroke, new Scalar(Constants.COLOR_YELLOW));
        ImageDrawer.drawStrokeIndex(img, stroke, new Scalar(Constants.COLOR_RED), strokeIndex);
        strokeEvaluation.outputMat = img;
        return strokeEvaluation;
    }
}
