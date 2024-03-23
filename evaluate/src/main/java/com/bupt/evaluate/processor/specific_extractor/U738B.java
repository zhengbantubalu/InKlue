package com.bupt.evaluate.processor.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.processor.SpecificExtractor;

//王
public class U738B implements SpecificExtractor {

    public static final int strokeNum = 4;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new PointList());
        }
        try {
            //第一横
            strokes.get(0).add(points.get(Points.END).get(0));
            strokes.get(0).add(points.get(Points.END).get(1));
            strokes.get(0).add(points.get(Points.INTER).get(0));
            strokes.get(0).sort();
            //第二横
            strokes.get(1).add(points.get(Points.END).get(2));
            strokes.get(1).add(points.get(Points.END).get(3));
            strokes.get(1).add(points.get(Points.INTER).get(1));
            strokes.get(1).sort();
            //第三横
            strokes.get(2).add(points.get(Points.END).get(4));
            strokes.get(2).add(points.get(Points.END).get(5));
            strokes.get(2).add(points.get(Points.INTER).get(2));
            strokes.get(2).sort();
            //竖
            strokes.get(3).addAll(points.get(Points.INTER));
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
