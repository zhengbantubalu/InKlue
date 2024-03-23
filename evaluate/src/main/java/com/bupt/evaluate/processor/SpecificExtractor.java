package com.bupt.evaluate.processor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Strokes;

//特定汉字的笔画提取器，用于针对性提取特定某个汉字的笔画
public interface SpecificExtractor {
    //根据轮廓，特征点，轮廓关键点的树状结构提取特定汉字的笔画
    Strokes extractStrokes(Contours contours, Points points);
}
