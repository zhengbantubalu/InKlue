package com.bupt.evaluate.extractors;

import com.bupt.evaluate.Contours;
import com.bupt.evaluate.Extractor;
import com.bupt.evaluate.PointList;
import com.bupt.evaluate.Points;
import com.bupt.evaluate.Strokes;

//日
public class U65E5 implements Extractor {
    public static int strokeNum = 5;

    public void extract(Strokes strokes, Contours contours, Points points) {
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new PointList());
        }
        try {
            //中间横
            strokes.get(0).add(points.get(Points.END).get(0));
            strokes.get(0).add(points.get(Points.END).get(1));
            strokes.get(0).sort(0, true);
        } catch (NullPointerException ignored) {
        }
    }
}
