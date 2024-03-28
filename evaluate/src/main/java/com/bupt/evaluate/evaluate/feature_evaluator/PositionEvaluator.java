package com.bupt.evaluate.evaluate.feature_evaluator;

import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.evaluate.FeatureEvaluator;
import com.bupt.evaluate.util.Constants;

//笔画位置评价器
public class PositionEvaluator implements FeatureEvaluator {

    public int getScore(Stroke inputStroke, Stroke stdStroke) {
        PointEx inputMidpoint = inputStroke.line.getMidpoint();
        PointEx stdMidpoint = stdStroke.line.getMidpoint();
        double difference = (double) inputMidpoint.getDistance(stdMidpoint) / Constants.POSITION_CRITERION;
        return Math.max((int) (100 - (difference * 100)), 0);
    }

    public String getAdvice(Stroke inputStroke, Stroke stdStroke) {
        PointEx inputMidpoint = inputStroke.line.getMidpoint();
        PointEx stdMidpoint = stdStroke.line.getMidpoint();
        double angle = stdStroke.line.getAngle();
        double distance = inputStroke.line.getDistance(stdStroke.line);
        StringBuilder advice = new StringBuilder();
        if (angle > 45 && angle < 135) {
            //竖直笔画
            if (distance > Constants.MIN_OFFSET) {
                //垂直笔画方向偏移
                if (inputMidpoint.x < stdMidpoint.x) {
                    advice.append("靠右书写");
                } else {
                    advice.append("靠左书写");
                }
            } else {
                //沿笔画方向偏移
                if (inputStroke.line.p1.getDistance(stdStroke.line.p1) > Constants.MIN_OFFSET) {
                    if (inputStroke.line.p1.y < stdStroke.line.p1.y) {
                        advice.append("靠下起笔");
                    } else {
                        advice.append("靠上起笔");
                    }
                }
                if (inputStroke.line.p2.getDistance(stdStroke.line.p2) > Constants.MIN_OFFSET) {
                    if (inputStroke.line.p2.y < stdStroke.line.p2.y) {
                        advice.append("靠下收笔");
                    } else {
                        advice.append("靠上收笔");
                    }
                }
            }
        } else {
            //水平笔画
            if (distance > Constants.MIN_OFFSET) {
                //垂直笔画方向偏移
                if (inputMidpoint.y < stdMidpoint.y) {
                    advice.append("靠下书写");
                } else {
                    advice.append("靠上书写");
                }
            } else {
                //沿笔画方向偏移
                if (inputStroke.line.p1.getDistance(stdStroke.line.p1) > Constants.MIN_OFFSET) {
                    if (inputStroke.line.p1.x < stdStroke.line.p1.x) {
                        advice.append("靠右起笔");
                    } else {
                        advice.append("靠左起笔");
                    }
                }
                if (inputStroke.line.p2.getDistance(stdStroke.line.p2) > Constants.MIN_OFFSET) {
                    if (inputStroke.line.p2.x < stdStroke.line.p2.x) {
                        advice.append("靠右收笔");
                    } else {
                        advice.append("靠左收笔");
                    }
                }
            }
        }
        if (advice.length() == 0) {
            advice.append("注意书写位置");//避免所有条件都不符合时返回空字符串
        }
        return advice.toString();
    }
}
