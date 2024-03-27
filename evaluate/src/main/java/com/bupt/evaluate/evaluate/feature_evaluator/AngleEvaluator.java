package com.bupt.evaluate.evaluate.feature_evaluator;

import com.bupt.evaluate.data.Line;
import com.bupt.evaluate.evaluate.EvaluationBuilder;
import com.bupt.evaluate.util.Constants;
import com.bupt.evaluate.util.ImageDrawer;

import org.opencv.core.Scalar;

//笔画倾角评价器
public class AngleEvaluator {

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
        double inputAngle = inputLine.getAngle();
        double stdAngle = stdLine.getAngle();
        double angleDifference = Math.abs(inputAngle - stdAngle);
        double difference = Math.min(angleDifference, 180 - angleDifference) / Constants.ANGLE_CRITERION;
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
