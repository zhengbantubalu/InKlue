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
        int originalScore = Math.max((int) (Math.min(100 - (difference * 100), 100)), 0);
        //捞人算法，用于解决较短笔画评分低的问题
        //作者 苏崇博
        int k = Constants.MAX_LENGTH / Math.min(stdStroke.curve.getLength(), Constants.MAX_LENGTH);
        return (int) (Math.pow(originalScore, 1.0 / k) * Math.pow(100, 1 - 1.0 / k));
    }

    public String getAdvice(Stroke inputStroke, Stroke stdStroke) {
        return "依据图片改进";
    }
}
