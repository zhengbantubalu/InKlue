package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//十 曹全碑
public class U53410200 implements SpecificExtractor {

    public static final int strokeNum = 2;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //横
            PointList tempList1 = new PointList();
            tempList1.add(points.end.get(1));
            tempList1.add(points.end.get(2));
            tempList1.sort(0,true);
            strokes.get(0).addList(contours.getMatchContour(
                    tempList1.get(0), points.inter.get(0)), false);
            strokes.get(0).addList(contours.getMatchContour(
                    points.inter.get(0), tempList1.get(1)));
            //竖
            strokes.get(1).add(points.end.get(0));
            strokes.get(1).add(points.inter.get(0));
            strokes.get(1).add(points.end.get(-1));
            strokes.get(1).isStraight=true;
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
