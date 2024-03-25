package com.bupt.evaluate.evaluate;

import com.bupt.evaluate.data.Line;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.util.Constants;

//笔画笔直度评价器
public class LinearityEvaluator {

    public static StrokeEvaluation getEvaluation(Stroke stroke, Line line) {
        StrokeEvaluation strokeEvaluation = new StrokeEvaluation();
        strokeEvaluation.score = getScore(stroke, line);
        if (strokeEvaluation.score < Constants.MIN_SCORE) {
            strokeEvaluation.advice = getAdvice();
        }
        return strokeEvaluation;
    }

    private static int getScore(Stroke stroke, Line line) {
        int size = stroke.size();
        double[] distances = new double[size];
        double sum = 0;
        for (int i = 0; i < size; i++) {
            PointEx p = stroke.get(i);
            distances[i] = line.getDistance(p);
            sum += distances[i];
        }
        double meanDistance = sum / size;
        double sumSquares = 0;
        for (int i = 0; i < size; i++) {
            sumSquares += (distances[i] - meanDistance) * (distances[i] - meanDistance);
        }
        double difference = Math.sqrt(sumSquares / size) / 30;
        return Math.max((int) (100 - (difference * 100)), 0);
    }

    private static String getAdvice() {
        return "书写平直";
    }
}
