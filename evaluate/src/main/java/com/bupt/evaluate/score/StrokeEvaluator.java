package com.bupt.evaluate.score;

import com.bupt.evaluate.data.Stroke;

//笔画书写评价器，用于评价一个笔画的书写
public class StrokeEvaluator {

    //取得对一个笔画的评价
    public static StrokeEvaluation evaluateStroke(Stroke inputStroke, Stroke stdStroke) {
        StrokeEvaluation strokeEvaluation = new StrokeEvaluation();
        strokeEvaluation.score = inputStroke.getStraightness();
        return strokeEvaluation;
    }
}
