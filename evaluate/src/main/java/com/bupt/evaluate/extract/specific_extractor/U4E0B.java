package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//下
public class U4E0B implements SpecificExtractor {

    public static final int strokeNum = 3;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //第一横
            strokes.get(0).add(points.get(Points.END).get(0));
            strokes.get(0).add(points.get(Points.END).get(1));
            strokes.get(0).add(points.get(Points.INTER).get(0));
            strokes.get(0).sort();
            //第二横
            strokes.get(1).add(points.get(Points.INTER).get(1));
            strokes.get(1).add(points.get(Points.END).get(2));
            //竖
            strokes.get(2).addAll(contours.findMatchContour(
                    points.get(Points.INTER).get(0), points.get(Points.INTER).get(1)));
            strokes.get(2).addAll(contours.findMatchContour(
                    points.get(Points.INTER).get(1), points.get(Points.END).get(3)));
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
