package com.bupt.evaluate.processor.extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Strokes;

//笔画提取器，用于提取一个汉字的笔画(旧方法)
public interface Extractor {

    //从轮廓和点集中提取笔画
    void extract(Strokes strokes, Contours contours, Points points);
}
