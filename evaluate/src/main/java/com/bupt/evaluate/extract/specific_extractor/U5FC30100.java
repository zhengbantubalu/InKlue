package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//心 九成宫醴泉铭
public class U5FC30100 implements SpecificExtractor {

    public static final int strokeNum = 4;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //从左向右排序
            points.end.sort();
            //左点
            strokes.get(0).addList(contours.getMatchContour(
                    points.end.get(0), points.end.get(1)), true);
            //中点
            strokes.get(1).addList(contours.getMatchContour(
                    points.end.get(3), points.end.get(4)), true);
            //分辨右侧三个端点
            PointList tempList = new PointList();
            tempList.add(points.end.get(-1));
            tempList.add(points.end.get(-2));
            tempList.add(points.end.get(-3));
            tempList.sort(1, true);
            //右点
            strokes.get(2).addList(contours.getMatchContour(
                    tempList.get(0), tempList.get(1)), true);
            //卧钩
            strokes.get(3).addList(contours.getMatchContour(
                    points.end.get(2), tempList.get(2)), false);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
