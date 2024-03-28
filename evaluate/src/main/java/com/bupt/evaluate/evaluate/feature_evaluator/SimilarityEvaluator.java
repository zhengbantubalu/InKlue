package com.bupt.evaluate.evaluate.feature_evaluator;

import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.evaluate.FeatureEvaluator;
import com.bupt.evaluate.util.Constants;

//笔画相似度评价器
public class SimilarityEvaluator implements FeatureEvaluator {

    public int getScore(Stroke inputStroke, Stroke stdStroke) {
        int size = inputStroke.curve.size();
        double sum = 0;
        for (int i = 0; i < size; i++) {
            sum += Math.sqrt(inputStroke.curve.get(i).getDistance(stdStroke.curve.get(i)));
        }
        double difference = sum / size / Constants.SIMILARITY_CRITERION;
        return Math.max((int) (Math.min(100 - (difference * 100), 100)), 0);
    }

    public String getAdvice(Stroke inputStroke, Stroke stdStroke) {
        return "依据图片改进";
    }
}
