package com.bupt.evaluate.evaluate;

import com.bupt.evaluate.data.Line;
import com.bupt.evaluate.data.Stroke;

//笔画书写评价器，用于评价一个笔画的书写
public class StrokeEvaluator {

    //取得对一个笔画的评价
    public static StrokeEvaluation evaluateStroke(Stroke inputStroke, Stroke stdStroke) {
        //根据笔画是否是直线，采用不同的评价方法
        if (inputStroke.isStraight) {
            return evaluateStraight(inputStroke, stdStroke);
        } else {
            return evaluateCurve(inputStroke, stdStroke);
        }
    }

    //评价直线笔画
    private static StrokeEvaluation evaluateStraight(Stroke inputStroke, Stroke stdStroke) {
        //直线拟合
        Line inputLine = inputStroke.fitLine();
        Line stdLine = stdStroke.fitLine();
        //评价笔直度
        StrokeEvaluation linearityEvaluation = LinearityEvaluator.getEvaluation(inputStroke, inputLine);
        //评价长度
        StrokeEvaluation lengthEvaluation = LengthEvaluator.getEvaluation(inputLine, stdLine);
        //评价倾角
        StrokeEvaluation angleEvaluation = AngleEvaluator.getEvaluation(inputLine, stdLine);
        //评价位置
        StrokeEvaluation positionEvaluation = PositionEvaluator.getEvaluation(inputLine, stdLine);
        //设置评价数据
        StrokeEvaluation strokeEvaluation = new StrokeEvaluation();
        strokeEvaluation.score = (linearityEvaluation.score + lengthEvaluation.score +
                angleEvaluation.score + positionEvaluation.score) / 4;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("//");
        stringBuilder.append(" 笔直度:").append(linearityEvaluation.score);
        if (linearityEvaluation.advice != null) {
            stringBuilder.append(" ").append(linearityEvaluation.advice);
        }
        stringBuilder.append(" 长度:").append(lengthEvaluation.score);
        if (lengthEvaluation.advice != null) {
            stringBuilder.append(" ").append(lengthEvaluation.advice);
        }
        stringBuilder.append(" 倾角:").append(angleEvaluation.score);
        if (angleEvaluation.advice != null) {
            stringBuilder.append(" ").append(angleEvaluation.advice);
        }
        stringBuilder.append(" 位置:").append(positionEvaluation.score);
        if (positionEvaluation.advice != null) {
            stringBuilder.append(" ").append(positionEvaluation.advice);
        }
        stringBuilder.append(" 均分:").append(strokeEvaluation.score);
        stringBuilder.append("\n");
        strokeEvaluation.advice = stringBuilder.toString();
        return strokeEvaluation;
    }

    //评价曲线笔画
    private static StrokeEvaluation evaluateCurve(Stroke inputStroke, Stroke stdStroke) {
        StrokeEvaluation strokeEvaluation = new StrokeEvaluation();
        strokeEvaluation.score = 100;
        return strokeEvaluation;
    }
}
