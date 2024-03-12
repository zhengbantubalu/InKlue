package com.bupt.evaluate.extractors;

import com.bupt.evaluate.Contours;
import com.bupt.evaluate.Extractor;
import com.bupt.evaluate.PointList;
import com.bupt.evaluate.Points;
import com.bupt.evaluate.Strokes;

//石
public class U77F3 implements Extractor {
    public static int strokeNum = 6;

    public void extract(Strokes strokes, Contours contours, Points points) {
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new PointList());
        }
        try {
            //中间短横
            strokes.get(0).add(points.get(Points.INTER).get(0));
            strokes.get(0).add(points.get(Points.INTER).get(1));
            strokes.get(0).sort(0, true);
        } catch (NullPointerException ignored) {
        }
    }
}
