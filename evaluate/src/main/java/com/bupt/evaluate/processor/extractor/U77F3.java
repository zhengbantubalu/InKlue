package com.bupt.evaluate.processor.extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Strokes;

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
