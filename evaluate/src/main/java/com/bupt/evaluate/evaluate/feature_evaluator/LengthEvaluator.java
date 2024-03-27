package com.bupt.evaluate.evaluate.feature_evaluator;

import com.bupt.evaluate.data.Line;
import com.bupt.evaluate.evaluate.EvaluationBuilder;
import com.bupt.evaluate.util.Constants;
import com.bupt.evaluate.util.ImageDrawer;

import org.opencv.core.Scalar;

//笔画长度评价器
public class LengthEvaluator {

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
        int inputLength = inputLine.getLength();
        int stdLength = stdLine.getLength();
        double difference = (double) Math.abs(inputLength - stdLength) / Constants.LENGTH_CRITERION;
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
