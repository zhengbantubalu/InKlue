package com.bupt.evaluate.evaluate.feature_evaluator;

import com.bupt.evaluate.data.Curve;
import com.bupt.evaluate.evaluate.EvaluationBuilder;
import com.bupt.evaluate.util.Constants;
import com.bupt.evaluate.util.ImageDrawer;

import org.opencv.core.Scalar;

//笔画相似度评价器
public class SimilarityEvaluator {

    public static void evaluate(EvaluationBuilder evaluationBuilder, Curve inputCurve, Curve stdCurve, int strokeIndex) {
        int score = getScore(inputCurve, stdCurve);
        evaluationBuilder.scores.add(score);
        if (score < Constants.MIN_SCORE) {
            evaluationBuilder.advices.add(getAdvice(inputCurve, stdCurve));
            if (!evaluationBuilder.isDrawn) {
                ImageDrawer.drawCurve(evaluationBuilder.outputMat, stdCurve,
                        new Scalar(Constants.COLOR_GREEN), Constants.THICKNESS);
                ImageDrawer.drawText(evaluationBuilder.outputMat, Integer.toString(strokeIndex + 1),
                        inputCurve.getQuartPoint(), new Scalar(Constants.COLOR_RED));
            }
        }
    }

    private static int getScore(Curve inputCurve, Curve stdCurve) {
        int size = inputCurve.size();
        double sum = 0;
        for (int i = 0; i < size; i++) {
            sum += Math.sqrt(inputCurve.get(i).getDistance(stdCurve.get(i)));
        }
        double difference = sum / size / Constants.SIMILARITY_CRITERION;
        return Math.max((int) (Math.min(100 - (difference * 100), 100)), 0);
    }

    private static String getAdvice(Curve inputCurve, Curve stdCurve) {
        return "依据图片改进";
    }
}
