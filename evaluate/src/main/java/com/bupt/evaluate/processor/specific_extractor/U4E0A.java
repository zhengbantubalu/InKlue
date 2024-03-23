package com.bupt.evaluate.processor.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.processor.SpecificExtractor;

//上
public class U4E0A implements SpecificExtractor {

    public static final int strokeNum = 3;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new PointList());
        }
        try {
            //第一横
            strokes.get(0).add(points.get(Points.INTER).get(0));
            strokes.get(0).add(points.get(Points.END).get(1));
            //第二横
            strokes.get(1).add(points.get(Points.END).get(2));
            strokes.get(1).add(points.get(Points.END).get(3));
            strokes.get(1).add(points.get(Points.INTER).get(1));
            strokes.get(1).sort();
            //竖
            strokes.get(2).addAll(contours.findMatchContour(
                    points.get(Points.END).get(0), points.get(Points.INTER).get(1)));
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
