package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//大 九成宫醴泉铭
public class U59270100 implements SpecificExtractor {

    public static final int strokeNum = 3;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //分辨横的左右端点
            PointList tempList1 = new PointList();
            tempList1.add(points.end.get(1));
            tempList1.add(points.end.get(2));
            tempList1.sort();
            //分辨撇捺的终点
            PointList tempList2 = new PointList();
            tempList2.add(points.end.get(-1));
            tempList2.add(points.end.get(-2));
            tempList2.sort();
            //横
            strokes.get(0).addList(contours.getMatchContour(
                    tempList1.get(0), points.inter.get(0)), true);
            strokes.get(0).addList(contours.getMatchContour(
                    points.inter.get(0), tempList1.get(1)));
            //撇
            strokes.get(1).addList(contours.getMatchContour(
                    points.end.get(0), points.inter.get(0)), false);
            strokes.get(1).addList(contours.getMatchContour(
                    points.inter.get(0), tempList2.get(0)));
            //捺
            strokes.get(2).addList(contours.getMatchContour(
                    points.inter.get(0), tempList2.get(1)), false);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
