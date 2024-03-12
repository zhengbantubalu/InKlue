package com.bupt.evaluate.extractors;

import com.bupt.evaluate.Contours;
import com.bupt.evaluate.Extractor;
import com.bupt.evaluate.PointList;
import com.bupt.evaluate.Strokes;
import com.bupt.evaluate.Points;

//五
public class U4E94 implements Extractor {
    public static int strokeNum = 4;

    public void extract(Strokes strokes, Contours contours, Points points) {
        points.get(Points.END).sort(1, true);
        points.get(Points.INTER).sort(1, true);
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new PointList());
        }
        strokes.get(0).add(points.get(Points.END).get(0));
        strokes.get(0).add(points.get(Points.END).get(1));
        strokes.get(0).sort(0, true);
        strokes.get(1).add(points.get(Points.END).get(-1));
        strokes.get(1).add(points.get(Points.END).get(-2));
        strokes.get(1).sort(0, true);
        PointList tempList1 = new PointList();
        tempList1.add(points.get(Points.INTER).get(0));
        tempList1.add(points.get(Points.INTER).get(1));
        tempList1.sort(0, true);
        PointList tempList2 = new PointList();
        tempList2.add(points.get(Points.INTER).get(-1));
        tempList2.add(points.get(Points.INTER).get(-2));
        tempList2.sort(0, true);
        strokes.get(2).add(tempList1.get(1));
        strokes.get(2).add(tempList2.get(0));
        strokes.get(3).add(tempList1.get(0));
        strokes.get(3).add(tempList2.get(1));
    }
}
