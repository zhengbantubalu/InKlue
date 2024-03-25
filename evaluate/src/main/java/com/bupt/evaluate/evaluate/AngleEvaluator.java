package com.bupt.evaluate.evaluate;

import com.bupt.evaluate.data.Line;
import com.bupt.evaluate.util.Constants;

//笔画倾角评价器
public class AngleEvaluator {

    public static StrokeEvaluation getEvaluation(Line inputLine, Line stdLine) {
        StrokeEvaluation strokeEvaluation = new StrokeEvaluation();
        strokeEvaluation.score = getScore(inputLine, stdLine);
        if (strokeEvaluation.score < Constants.MIN_SCORE) {
            strokeEvaluation.advice = getAdvice(inputLine, stdLine);
        }
        return strokeEvaluation;
    }

    private static int getScore(Line inputLine, Line stdLine) {
        double inputAngle = inputLine.getAngle();
        double stdAngle = stdLine.getAngle();
        double angleDifference = Math.abs(inputAngle - stdAngle);
        double difference = Math.min(angleDifference, 180 - angleDifference) / 30;
        return Math.max((int) (100 - (difference * 100)), 0);
    }

    private static String getAdvice(Line inputLine, Line stdLine) {
        double inputAngle = inputLine.getAngle();
        double stdAngle = stdLine.getAngle();
        String advice;
        if (Math.abs(inputAngle - stdAngle) > 90) {
            if (inputAngle < stdAngle) {
                advice = "向左倾斜";
            } else {
                advice = "向右倾斜";
            }
        } else {
            if (inputAngle > stdAngle) {
                advice = "向左倾斜";
            } else {
                advice = "向右倾斜";
            }
        }
        return advice;
    }
}
