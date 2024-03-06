package com.bupt.evaluate;

//特定提取器，用于针对性提取特定单个汉字的笔画
public interface Extractor {
    void extract(Strokes strokes, Points points);
}
