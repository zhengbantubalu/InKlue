package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//工 九成宫醴泉铭
public class U5DE50100 implements SpecificExtractor {

    public static final int strokeNum = 3;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //从左向右排序
            points.end.sort();
            //短横
            strokes.get(0).addList(contours.getMatchContour(
                    points.end.get(1), points.end.get(2)), false);
            //竖
            strokes.get(1).addList(contours.getMatchContour(
                    points.inter.get(0), points.inter.get(1)), false);
            //长横
            strokes.get(2).addList(contours.getMatchContour(
                    points.end.get(0), points.end.get(-1)), false);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
