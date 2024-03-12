package com.bupt.evaluate.extractors;

import com.bupt.evaluate.Contours;
import com.bupt.evaluate.Extractor;
import com.bupt.evaluate.PointList;
import com.bupt.evaluate.Points;
import com.bupt.evaluate.Strokes;

//下
public class U4E0B implements Extractor {
    public static int strokeNum = 3;

    public void extract(Strokes strokes, Contours contours, Points points) {
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new PointList());
        }
        try {
            //第一横
            strokes.get(0).add(points.get(Points.END).get(0));
            strokes.get(0).add(points.get(Points.END).get(1));
            strokes.get(0).add(points.get(Points.INTER).get(0));
            strokes.get(0).sort(0, true);
            //第二横
            strokes.get(1).add(points.get(Points.INTER).get(1));
            strokes.get(1).add(points.get(Points.END).get(2));
            //竖
            strokes.get(2).addAll(contours.findMatchContour(
                    points.get(Points.INTER).get(0), points.get(Points.END).get(3)));
        } catch (NullPointerException ignored) {
        }
    }
}
