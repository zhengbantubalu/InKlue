package com.bupt.evaluate;

import com.bupt.evaluate.utils.Transcoder;

import java.util.ArrayList;

//一个汉字的所有笔画信息，每个元素是类型为PointList的一个笔画，每个笔画的元素是Point类
public class Strokes extends ArrayList<PointList> {

    //从点集中提取笔画
    public Strokes(String cnChar, Points points) {
        String unicode = Transcoder.UTF2CodePoint(cnChar);//取得汉字的Unicode码点字符串
        Extractor extractor = createInstance(unicode);//根据码点创建特定提取器实例
        if (extractor != null) {
            extractor.extract(this, points);//调用特定提取器的提取方法
        }
    }

    //创建特定提取器实例
    private static Extractor createInstance(String unicode) {
        String fullClassName = "com.bupt.evaluate.extractors." + unicode;
        try {
            Class<?> clazz = Class.forName(fullClassName);
            return (Extractor) clazz.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            return null;
        }
    }
}
