package com.bupt.evaluate.extract;

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
            //创建实例成功，则调用笔画提取器的提取方法，并返回
            return specificExtractor.extractStrokes(contours, points);
        }
        //创建实例失败，返回空的笔画类
        return new Strokes();
    }
}
