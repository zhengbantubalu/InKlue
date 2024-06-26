package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;
import com.bupt.evaluate.util.Constants;

//也 圣教序
public class U4E5F0400 implements SpecificExtractor {

    public static final int strokeNum = 4;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //横
            points.end.sort();
            strokes.get(0).addList(contours.getMatchContour(
                    points.end.get(0), points.inter.get(1)), true);
            strokes.get(0).addList(contours.getMatchContour(
                    points.inter.get(1), points.inter.get(0)), true);
            strokes.get(0).addList(contours.getMatchContour(
                    points.inter.get(0), points.end.get(5)), true);
            //折
            strokes.get(1).addList(contours.getMatchContour(
                    points.end.get(5), points.end.get(4)), true);
            //竖
            points.end.sort(1,true);
            strokes.get(2).addList(contours.getMatchContour(
                    points.end.get(0), points.inter.get(0)), true);
            PointList tempList1 = new PointList();
            tempList1.add(points.end.get(4));
            tempList1.add(points.end.get(5));
            tempList1.sort();
            strokes.get(2).addList(contours.getMatchContour(
                    points.inter.get(0), tempList1.get(0)), true);
            //竖折
            points.inter.sort();
            strokes.get(3).add(points.end.get(2));
            strokes.get(3).addList(contours.getMatchContour(
                    points.inter.get(0), points.end.get(-1)), false);

        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
