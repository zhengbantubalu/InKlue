package com.bupt.evaluate.evaluate;

import com.bupt.evaluate.data.Line;
import com.bupt.evaluate.util.Constants;

//笔画长度评价器
public class LengthEvaluator {

    public static StrokeEvaluation getEvaluation(Line inputLine, Line stdLine) {
        StrokeEvaluation strokeEvaluation = new StrokeEvaluation();
        strokeEvaluation.score = getScore(inputLine, stdLine);
        if (strokeEvaluation.score < Constants.MIN_SCORE) {
            strokeEvaluation.advice = getAdvice(inputLine, stdLine);
        }
        return strokeEvaluation;
    }

    private static int getScore(Line inputLine, Line stdLine) {
        int inputLength = inputLine.getLength();
        int stdLength = stdLine.getLength();
        double difference = (double) Math.abs(inputLength - stdLength) / stdLength * 2;
        return Math.max((int) (100 - (difference * 100)), 0);
    }

    private static String getAdvice(Line inputLine, Line stdLine) {
        int inputLength = inputLine.getLength();
        int stdLength = stdLine.getLength();
        if (inputLength < stdLength) {
            return "写长一些";
        } else {
            return "写短一些";
        }
    }
}
