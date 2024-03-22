package com.bupt.evaluate.data;

import com.bupt.evaluate.processor.extractor.Extractor;
import com.bupt.evaluate.processor.extractor.ExtractorFactory;

import java.util.ArrayList;

//一个汉字的笔画列表，每个元素是类型为PointList的一个笔画，每个笔画的元素是PointEx类
public class Strokes extends ArrayList<PointList> {

    //空构造方法，用于创建空笔画列表
    public Strokes() {
    }

    //从轮廓和点集中提取笔画(旧方法)
    public Strokes(String cnChar, Contours contours, Points points) {
        //根据汉字名创建指定的笔画提取器实例
        Extractor extractor = ExtractorFactory.createInstance(cnChar);
        if (extractor != null) {
            extractor.extract(this, contours, points);//调用笔画提取器的提取方法
        }
    }
}
