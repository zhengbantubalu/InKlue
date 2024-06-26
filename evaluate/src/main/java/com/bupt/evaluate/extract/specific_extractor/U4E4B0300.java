package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;
import com.bupt.evaluate.util.Constants;

//之 张猛龙碑·壹
public class U4E4B0300 implements SpecificExtractor {

    public static final int strokeNum = 4;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //获取右上角点
            PointEx pointEx1 = contours.getNearestPoint(
                    new PointEx(Constants.IMAGE_SIZE, Constants.IMAGE_SIZE/4));
            //点
            strokes.get(0).addList(contours.getMatchContour(
                    points.end.get(0), points.end.get(1)), true);
            //从左向右排序
            points.end.sort();
            //横
            strokes.get(1).addList(contours.getMatchContour(
                    points.end.get(1), pointEx1), true);
            //撇
            strokes.get(2).addList(contours.getMatchContour(
                    pointEx1, points.end.get(0)), false);
            //捺
            strokes.get(3).addList(contours.getMatchContour(
                    points.end.get(0), points.end.get(-1)), false);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
