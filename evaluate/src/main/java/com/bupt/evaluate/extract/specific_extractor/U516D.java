package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//六
public class U516D implements SpecificExtractor {

    public static final int strokeNum = 5;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //从左向右排序
            points.get(Points.INTER).sort();
            //顶部短竖
            strokes.get(0).add(points.get(Points.END).get(0));
            strokes.get(0).add(points.get(Points.INTER).get(1));
            strokes.get(0).isStraight = true;
            //分辨两个横的端点
            PointList tempList1 = new PointList();
            tempList1.add(points.get(Points.END).get(1));
            tempList1.add(points.get(Points.END).get(2));
            tempList1.sort();
            //左半弯横
            strokes.get(1).addList(contours.getMatchContour(
                    points.get(Points.INTER).get(1), tempList1.get(0)), false);
            //右半弯横
            strokes.get(2).addList(contours.getMatchContour(
                    points.get(Points.INTER).get(1), tempList1.get(1)), false);
            //分辨两个竖的端点
            PointList tempList2 = new PointList();
            tempList2.add(points.get(Points.END).get(-1));
            tempList2.add(points.get(Points.END).get(-2));
            tempList2.sort();
            //左边弯竖
            strokes.get(3).addList(contours.getMatchContour(
                    points.get(Points.INTER).get(0), tempList2.get(0)), false);
            //右边弯竖
            strokes.get(4).addList(contours.getMatchContour(
                    points.get(Points.INTER).get(2), tempList2.get(1)), false);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
