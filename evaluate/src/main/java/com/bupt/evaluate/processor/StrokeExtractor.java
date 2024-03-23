package com.bupt.evaluate.processor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Strokes;

//笔画提取器，用于提取一个汉字的笔画
public class StrokeExtractor {
    //根据轮廓和特征点提取汉字笔画
    public static Strokes extractStrokes(String cnChar, Contours contours, Points points) {
        //根据汉字名创建指定的笔画提取器实例
        SpecificExtractor specificExtractor = ExtractorFactory.createInstance(cnChar);
        if (specificExtractor != null) {
            return specificExtractor.extractStrokes(contours, points);//调用笔画提取器的提取方法
        }
        return new Strokes();
    }
}
