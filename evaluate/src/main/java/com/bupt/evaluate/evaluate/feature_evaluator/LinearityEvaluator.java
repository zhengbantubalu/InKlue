package com.bupt.evaluate.evaluate.feature_evaluator;

import com.bupt.evaluate.data.Line;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.evaluate.EvaluationBuilder;
import com.bupt.evaluate.util.Constants;
import com.bupt.evaluate.util.ImageDrawer;

import org.opencv.core.Scalar;

//笔画平直度评价器
public class LinearityEvaluator {

    public static void evaluate(EvaluationBuilder evaluationBuilder,
                                Stroke stroke, Line inputLine, Line stdLine, int strokeIndex) {
        int score = getScore(stroke, inputLine);
        evaluationBuilder.scores.add(score);
        if (score < Constants.MIN_SCORE) {
            evaluationBuilder.advices.add(getAdvice());
            if (!evaluationBuilder.isDrawn) {
                ImageDrawer.drawLine(evaluationBuilder.outputMat, stdLine,
                        new Scalar(Constants.COLOR_GREEN), Constants.THICKNESS);
                ImageDrawer.drawText(evaluationBuilder.outputMat, Integer.toString(strokeIndex + 1),
                        inputLine.getQuartPoint(), new Scalar(Constants.COLOR_RED));
            }
        }
    }

    private static int getScore(Stroke stroke, Line line) {
        int size = stroke.size();
        double[] distances = new double[size];
        double sum = 0;
        for (int i = 0; i < size; i++) {
            distances[i] = line.getDistance(stroke.get(i));
            sum += distances[i];
        }
        double meanDistance = sum / size;
        double sumSquares = 0;
        for (int i = 0; i < size; i++) {
            sumSquares += (distances[i] - meanDistance) * (distances[i] - meanDistance);
        }
        double difference = Math.sqrt(sumSquares / size) / Constants.LINEARITY_CRITERION;
        return Math.max((int) (Math.min(105 - (difference * 100), 100)), 0);
    }

    private static String getAdvice() {
        return "书写平直";
    }
}
