package com.bupt.evaluate.processor.extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Strokes;

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
