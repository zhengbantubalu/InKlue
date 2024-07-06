package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//工 曹全碑
public class U5DE50200 implements SpecificExtractor {

    public static final int strokeNum = 4;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //从左向右排序
            points.end.sort();
            //第一横
            strokes.get(0).addList(contours.getMatchContour(
                    points.end.get(1), points.end.get(-2)), false);
            //分辨撇和竖的起点
            PointList tempList = new PointList();
            tempList.add(points.end.get(3));
            tempList.add(points.end.get(4));
            tempList.sort(1, true);
            //撇
            strokes.get(1).addList(contours.getMatchContour(
                    tempList.get(0), points.end.get(2)), true);
            //竖
            strokes.get(2).addList(contours.getMatchContour(
                    tempList.get(1), points.inter.get(0)), false);
            //最后一横
            strokes.get(3).addList(contours.getMatchContour(
                    points.end.get(0), points.end.get(-1)), false);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
