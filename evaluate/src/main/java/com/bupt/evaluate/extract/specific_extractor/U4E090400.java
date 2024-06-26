package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;
import com.bupt.evaluate.util.Constants;

//三 圣教序
public class U4E090400 implements SpecificExtractor {

    public static final int strokeNum = 3;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            PointList tempList1 = new PointList();
            tempList1.add(points.end.get(0));
            tempList1.add(points.end.get(1));
            PointList tempList2 = new PointList();
            tempList2.add(points.end.get(2));
            tempList2.add(points.end.get(3));
            PointList tempList3 = new PointList();
            tempList3.add(points.end.get(4));
            tempList3.add(points.end.get(5));
            //第一横
            tempList1.sort();
            strokes.get(0).addList(contours.getMatchContour(
                    tempList1.get(0), tempList1.get(1)), true);
            //第二横
            tempList2.sort();
            strokes.get(1).addList(contours.getMatchContour(
                    tempList2.get(0), tempList2.get(1)), true);
            //第三横
            tempList3.sort();
            strokes.get(2).addList(contours.getMatchContour(
                    tempList3.get(0), tempList3.get(1)), true);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
