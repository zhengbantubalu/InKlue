package com.bupt.evaluate.evaluate.feature_evaluator;

import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.evaluate.FeatureEvaluator;
import com.bupt.evaluate.util.Constants;

//笔画长度评价器
public class LengthEvaluator implements FeatureEvaluator {

    public int getScore(Stroke inputStroke, Stroke stdStroke) {
        int inputLength = inputStroke.line.getLength();
        int stdLength = stdStroke.line.getLength();
        double difference = Math.pow(inputLength - stdLength, 2) / stdLength / Constants.LENGTH_CRITERION;
        return Math.max((int) (100 - (difference * 100)), 0);
    }

    public String getAdvice(Stroke inputStroke, Stroke stdStroke) {
        int inputLength = inputStroke.line.getLength();
        int stdLength = stdStroke.line.getLength();
        if (inputLength < stdLength) {
            return "写长一些";
        } else {
            return "写短一些";
        }
    }
}
