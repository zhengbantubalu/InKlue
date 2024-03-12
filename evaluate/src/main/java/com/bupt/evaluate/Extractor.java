package com.bupt.evaluate;

//特定提取器，用于针对性提取特定单个汉字的笔画
public interface Extractor {
    public static int strokeNum = 0;//笔画数

    //从点集中提取笔画
    void extract(Strokes strokes, Contours contours, Points points);
}
