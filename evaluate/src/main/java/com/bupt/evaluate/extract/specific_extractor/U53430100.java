package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//千 九成宫醴泉铭
public class U53430100 implements SpecificExtractor {

    public static final int strokeNum = 3;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //竖
            strokes.get(2).add(points.end.get(-4));
            strokes.get(2).addList(contours.getMatchContour(
                    points.inter.get(0), points.end.get(-1)), true);
            //从左向右排序
            points.end.sort();
            //撇
            strokes.get(0).addList(contours.getMatchContour(
                    points.end.get(-2), points.end.get(1)), true);
            //横
            strokes.get(1).addList(contours.getMatchContour(
                    points.end.get(0), points.inter.get(0)), true);
            strokes.get(1).addList(contours.getMatchContour(
                    points.inter.get(0), points.end.get(-1)));
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
