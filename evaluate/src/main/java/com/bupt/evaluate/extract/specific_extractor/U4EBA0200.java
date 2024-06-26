package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//人 曹全碑
public class U4EBA0200 implements SpecificExtractor {

    public static final int strokeNum = 2;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //撇
            strokes.get(0).addList(contours.getMatchContour(
                    points.end.get(0), points.end.get(1)), false);
            //捺
            strokes.get(1).addList(contours.getMatchContour(
                    points.inter.get(0), points.end.get(2)), false
            );

        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
