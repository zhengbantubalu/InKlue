package com.bupt.evaluate.evaluate;

import com.bupt.evaluate.data.Curve;
import com.bupt.evaluate.data.Line;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.evaluate.feature_evaluator.AngleEvaluator;
import com.bupt.evaluate.evaluate.feature_evaluator.LengthEvaluator;
import com.bupt.evaluate.evaluate.feature_evaluator.LinearityEvaluator;
import com.bupt.evaluate.evaluate.feature_evaluator.PositionEvaluator;
import com.bupt.evaluate.evaluate.feature_evaluator.SimilarityEvaluator;
import com.bupt.evaluate.util.Constants;

import org.opencv.core.Mat;

//笔画书写评价器，用于评价一个笔画的书写
public class StrokeEvaluator {

    //取得对一个笔画的评价
    public static StrokeEvaluation evaluateStroke(
            Stroke inputStroke, Stroke stdStroke, Mat img, int strokeIndex) {
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
        EvaluationBuilder evaluationBuilder = new EvaluationBuilder();
        evaluationBuilder.outputMat = img;
        //直线拟合
        Line inputLine = Line.fitLine(inputStroke);
        Line stdLine = Line.fitLine(stdStroke);
        //评价平直度
        LinearityEvaluator.evaluate(evaluationBuilder, inputStroke, inputLine, stdLine, strokeIndex);
        //评价长度
        LengthEvaluator.evaluate(evaluationBuilder, inputLine, stdLine, strokeIndex);
        //评价倾角
        AngleEvaluator.evaluate(evaluationBuilder, inputLine, stdLine, strokeIndex);
        //评价位置
        PositionEvaluator.evaluate(evaluationBuilder, inputLine, stdLine, strokeIndex);
        return evaluationBuilder;
    }

    //评价曲线笔画
    private static EvaluationBuilder evaluateCurve(
            Stroke inputStroke, Stroke stdStroke, Mat img, int strokeIndex) {
        //创建笔画评价构建器
        EvaluationBuilder evaluationBuilder = new EvaluationBuilder();
        evaluationBuilder.outputMat = img;
        //曲线插值
        int size = stdStroke.getLength() / Constants.STEP_SIZE + 1;//+1防止提取笔画失败导致size为0
        Curve inputCurve = Curve.interpolateCurve(inputStroke, size);
        Curve stdCurve = Curve.interpolateCurve(stdStroke, size);
        //评价相似度
        SimilarityEvaluator.evaluate(evaluationBuilder, inputCurve, stdCurve, strokeIndex);
        return evaluationBuilder;
    }
}
