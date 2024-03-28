package com.bupt.evaluate.evaluate.feature_evaluator;

import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.evaluate.FeatureEvaluator;
import com.bupt.evaluate.util.Constants;

//笔画平直度评价器
public class LinearityEvaluator implements FeatureEvaluator {

    public int getScore(Stroke inputStroke, Stroke stdStroke) {
        int size = inputStroke.size();
        double[] distances = new double[size];
        double sum = 0;
        for (int i = 0; i < size; i++) {
            distances[i] = inputStroke.line.getDistance(inputStroke.get(i));
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

    public String getAdvice(Stroke inputStroke, Stroke stdStroke) {
        return "书写平直";
    }
}
