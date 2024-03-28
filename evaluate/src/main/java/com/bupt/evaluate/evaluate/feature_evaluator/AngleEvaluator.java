package com.bupt.evaluate.evaluate.feature_evaluator;

import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.evaluate.FeatureEvaluator;
import com.bupt.evaluate.util.Constants;

//笔画倾角评价器
public class AngleEvaluator implements FeatureEvaluator {

    public int getScore(Stroke inputStroke, Stroke stdStroke) {
        double inputAngle = inputStroke.line.getAngle();
        double stdAngle = stdStroke.line.getAngle();
        double angleDifference = Math.abs(inputAngle - stdAngle);
        double difference = Math.min(angleDifference, 180 - angleDifference) / Constants.ANGLE_CRITERION;
        return Math.max((int) (100 - (difference * 100)), 0);
    }

    public String getAdvice(Stroke inputStroke, Stroke stdStroke) {
        double inputAngle = inputStroke.line.getAngle();
        double stdAngle = stdStroke.line.getAngle();
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
