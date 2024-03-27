package com.bupt.evaluate.evaluate.feature_evaluator;

import com.bupt.evaluate.data.Line;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.evaluate.EvaluationBuilder;
import com.bupt.evaluate.util.Constants;
import com.bupt.evaluate.util.ImageDrawer;

import org.opencv.core.Scalar;

//笔画位置评价器
public class PositionEvaluator {

    public static void evaluate(EvaluationBuilder evaluationBuilder, Line inputLine, Line stdLine, int strokeIndex) {
        int score = getScore(inputLine, stdLine);
        evaluationBuilder.scores.add(score);
        if (score < Constants.MIN_SCORE) {
            evaluationBuilder.advices.add(getAdvice(inputLine, stdLine));
            if (!evaluationBuilder.isDrawn) {
                ImageDrawer.drawLine(evaluationBuilder.outputMat, stdLine,
                        new Scalar(Constants.COLOR_GREEN), Constants.THICKNESS);
                ImageDrawer.drawText(evaluationBuilder.outputMat, Integer.toString(strokeIndex + 1),
                        inputLine.getQuartPoint(), new Scalar(Constants.COLOR_RED));
            }
        }
    }

    private static int getScore(Line inputLine, Line stdLine) {
        PointEx inputMidpoint = inputLine.getMidpoint();
        PointEx stdMidpoint = stdLine.getMidpoint();
        double difference = (double) inputMidpoint.getDistance(stdMidpoint) / Constants.POSITION_CRITERION;
        return Math.max((int) (100 - (difference * 100)), 0);
    }

    private static String getAdvice(Line inputLine, Line stdLine) {
        double angle = stdLine.getAngle();
        StringBuilder advice = new StringBuilder();
        if (angle > 45 && angle < 135) {
            if (inputLine.p1.getDistance(stdLine.p1) > Constants.MIN_OFFSET) {
                if (inputLine.p1.y < stdLine.p1.y) {
                    advice.append("靠下起笔");
                } else {
                    advice.append("靠上起笔");
                }
            }
            if (inputLine.p2.getDistance(stdLine.p2) > Constants.MIN_OFFSET) {
                if (inputLine.p2.y < stdLine.p2.y) {
                    advice.append("靠下收笔");
                } else {
                    advice.append("靠上收笔");
                }
            }
        } else {
            if (inputLine.p1.getDistance(stdLine.p1) > Constants.MIN_OFFSET) {
                if (inputLine.p1.x < stdLine.p1.x) {
                    advice.append("靠右起笔");
                } else {
                    advice.append("靠左起笔");
                }
            }
            if (inputLine.p2.getDistance(stdLine.p2) > Constants.MIN_OFFSET) {
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
