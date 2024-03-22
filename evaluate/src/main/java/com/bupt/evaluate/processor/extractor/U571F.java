package com.bupt.evaluate.processor.extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.data.Points;

//土
public class U571F implements Extractor {
    public static int strokeNum = 3;

    public void extract(Strokes strokes, Contours contours, Points points) {
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new PointList());
        }
        try {
            //第一横
            strokes.get(0).add(points.get(Points.END).get(1));
            strokes.get(0).add(points.get(Points.END).get(2));
            strokes.get(0).add(points.get(Points.INTER).get(0));
            strokes.get(0).sort(0, true);
            //第二横
            strokes.get(1).add(points.get(Points.END).get(3));
            strokes.get(1).add(points.get(Points.END).get(4));
            strokes.get(1).add(points.get(Points.INTER).get(1));
            strokes.get(1).sort(0, true);
            //竖
            strokes.get(2).add(points.get(Points.END).get(0));
            strokes.get(2).addAll(points.get(Points.INTER));
        } catch (NullPointerException ignored) {
        }
    }
}
