package com.bupt.evaluate.evaluate;

import com.bupt.evaluate.data.Stroke;

//笔画特征评价器
public interface FeatureEvaluator {

    //获取评分
    int getScore(Stroke inputStroke, Stroke stdStroke);

    //获取建议
    String getAdvice(Stroke inputStroke, Stroke stdStroke);
}
