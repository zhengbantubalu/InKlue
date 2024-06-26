package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;
import com.bupt.evaluate.util.Constants;

//以 圣教序
public class U59290400 implements SpecificExtractor {

    public static final int strokeNum = 5;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //第一横
            PointList tempList1 = new PointList();
            tempList1.add(points.end.get(0));
            tempList1.add(points.end.get(1));
            tempList1.sort();
            strokes.get(0).addList(contours.getMatchContour(
                    tempList1.get(0), tempList1.get(1)), true);
            PointList tempList2 = new PointList();
            tempList2.add(points.end.get(3));
            tempList2.add(points.end.get(4));
            tempList2.sort();
            strokes.get(1).addList(contours.getMatchContour(
                    tempList2.get(0), tempList2.get(1)), true);
            strokes.get(2).addList(contours.getMatchContour(
                    points.end.get(2), points.inter.get(1)), true);
            PointList tempList3 = new PointList();
            tempList3.add(points.end.get(5));
            tempList3.add(points.end.get(6));
            tempList3.sort();
            strokes.get(3).addList(contours.getMatchContour(
                    points.inter.get(1), tempList3.get(0)), false);
            strokes.get(4).addList(contours.getMatchContour(
                    points.inter.get(1), tempList3.get(1)), false);

        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
