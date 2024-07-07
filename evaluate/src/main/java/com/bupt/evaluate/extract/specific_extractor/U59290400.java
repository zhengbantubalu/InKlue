package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//天 圣教序
public class U59290400 implements SpecificExtractor {

    public static final int strokeNum = 4;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //第一横
            strokes.get(0).addList(contours.getMatchContour(
                    points.end.get(0), points.end.get(1)), true);
            //分辨撇捺的端点
            PointList tempList2 = new PointList();
            tempList2.add(points.end.get(5));
            tempList2.add(points.end.get(6));
            tempList2.sort();
            //撇
            strokes.get(2).add(points.end.get(2));
            strokes.get(2).add(points.inter.get(0));
            strokes.get(2).addList(contours.getMatchContour(
                    points.inter.get(-1), tempList2.get(0)), false);
            //捺
            strokes.get(3).addList(contours.getMatchContour(
                    points.inter.get(-1), tempList2.get(1)), false);
            //从左向右排序
            points.inter.sort();
            //分辨第二横的左右端点
            PointList tempList1 = new PointList();
            tempList1.add(points.end.get(3));
            tempList1.add(points.end.get(4));
            tempList1.sort();
            //第二横
            strokes.get(1).addList(contours.getMatchContour(
                    tempList1.get(0), points.inter.get(0)), true);
            strokes.get(1).addList(contours.getMatchContour(
                    points.inter.get(-1), tempList1.get(1)));
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
