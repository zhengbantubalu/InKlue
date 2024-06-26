package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//主 曹全碑
public class U4E3B0200 implements SpecificExtractor {

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
            tempList1.sort(0,true);
            strokes.get(0).addList(contours.getMatchContour(
                    tempList1.get(0), tempList1.get(1)), true);
            //第二横
            PointList tempList2 = new PointList();
            tempList2.add(points.end.get(2));
            tempList2.add(points.end.get(3));
            tempList2.sort(0,true);
            strokes.get(1).addList(contours.getMatchContour(
                    tempList2.get(0), tempList2.get(1)), true);
            //第三横
            PointList tempList3 = new PointList();
            tempList3.add(points.end.get(4));
            tempList3.add(points.end.get(5));
            tempList3.sort(0,true);
            strokes.get(2).addList(contours.getMatchContour(
                    tempList3.get(0), points.inter.get(1)), true);
            strokes.get(2).addList(contours.getMatchContour(
                    points.inter.get(1), tempList3.get(1)));
            //第四竖
            strokes.get(3).addList(contours.getMatchContour(
                    points.inter.get(0), points.inter.get(1)), true);
            strokes.get(3).addList(contours.getMatchContour(
                    points.inter.get(1),points.inter.get(2)));
            //最后一横
            PointList tempList4 = new PointList();
            tempList4.add(points.end.get(-1));
            tempList4.add(points.end.get(-2));
            tempList4.sort(0,true);
            strokes.get(4).addList(contours.getMatchContour(
                    tempList4.get(0), tempList4.get(1)), false);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}

