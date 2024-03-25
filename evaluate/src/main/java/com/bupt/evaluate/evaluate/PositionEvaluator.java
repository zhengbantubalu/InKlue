package com.bupt.evaluate.evaluate;

import com.bupt.evaluate.data.Line;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.util.Constants;

//笔画位置评价器
public class PositionEvaluator {

    public static StrokeEvaluation getEvaluation(Line inputLine, Line stdLine) {
        StrokeEvaluation strokeEvaluation = new StrokeEvaluation();
        strokeEvaluation.score = getScore(inputLine, stdLine);
        if (strokeEvaluation.score < Constants.MIN_SCORE) {
            strokeEvaluation.advice = getAdvice(inputLine, stdLine);
        }
        return strokeEvaluation;
    }

    private static int getScore(Line inputLine, Line stdLine) {
        PointEx inputMidpoint = inputLine.getMidpoint();
        PointEx stdMidpoint = stdLine.getMidpoint();
        double difference = (double) inputMidpoint.getDistance(stdMidpoint) / Constants.IMAGE_SIZE * 6;
        return Math.max((int) (100 - (difference * 100)), 0);
    }

    private static String getAdvice(Line inputLine, Line stdLine) {
        double angle = stdLine.getAngle();
        StringBuilder advice = new StringBuilder();
        if (angle > 45 && angle < 135) {
            if (inputLine.p1.getDistance(stdLine.p1) > Constants.IMAGE_SIZE / 20) {
                if (inputLine.p1.y < stdLine.p1.y) {
                    advice.append("靠下起笔");
                } else {
                    advice.append("靠上起笔");
                }
            }
            if (inputLine.p2.getDistance(stdLine.p2) > Constants.IMAGE_SIZE / 20) {
                if (inputLine.p2.y < stdLine.p2.y) {
                    advice.append("靠下收笔");
                } else {
                    advice.append("靠上收笔");
                }
            }
        } else {
            if (inputLine.p1.getDistance(stdLine.p1) > Constants.IMAGE_SIZE / 20) {
                if (inputLine.p1.x < stdLine.p1.x) {
                    advice.append("靠右起笔");
                } else {
                    advice.append("靠左起笔");
                }
            }
            if (inputLine.p2.getDistance(stdLine.p2) > Constants.IMAGE_SIZE / 20) {
                if (inputLine.p2.x < stdLine.p2.x) {
                    advice.append("靠右收笔");
                } else {
                    advice.append("靠左收笔");
                }
            }
        }
        return advice.toString();
    }
}
