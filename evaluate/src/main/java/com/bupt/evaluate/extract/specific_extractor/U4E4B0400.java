package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//之 圣教序
public class U4E4B0400 implements SpecificExtractor {

    public static final int strokeNum = 2;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //点
            strokes.get(0).addList(contours.getMatchContour(
                    points.end.get(0), points.end.get(1)), true);
            //撇捺
            strokes.get(1).addList(contours.getMatchContour(
                    points.end.get(2), points.end.get(3)), false);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
