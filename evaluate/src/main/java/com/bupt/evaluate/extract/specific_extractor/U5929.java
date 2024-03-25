package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//天
public class U5929 implements SpecificExtractor {

    public static final int strokeNum = 6;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //第一横
            PointList tempList1 = new PointList();
            tempList1.add(points.get(Points.END).get(0));
            tempList1.add(points.get(Points.END).get(1));
            tempList1.sort();
            strokes.get(0).addList(contours.getMatchContour(
                    tempList1.get(0), tempList1.get(1)), true);
            //分辨三个交点
            PointList tempList2 = new PointList();
            tempList2.add(points.get(Points.INTER).get(1));
            tempList2.add(points.get(Points.INTER).get(2));
            tempList2.add(points.get(Points.INTER).get(3));
            tempList2.sort();
            //顶部短竖
            strokes.get(1).add(points.get(Points.INTER).get(0));
            strokes.get(1).add(tempList2.get(1));
            strokes.get(1).isStraight = true;
            //分辨两个横的端点
            PointList tempList3 = new PointList();
            tempList3.add(points.get(Points.END).get(2));
            tempList3.add(points.get(Points.END).get(3));
            tempList3.sort();
            //左半弯横
            strokes.get(2).addList(contours.getMatchContour(
                    tempList2.get(1), tempList3.get(0)), false);
            //右半弯横
            strokes.get(3).addList(contours.getMatchContour(
                    tempList2.get(1), tempList3.get(1)), false);
            //分辨两个竖的端点
            PointList tempList4 = new PointList();
            tempList4.add(points.get(Points.END).get(-1));
            tempList4.add(points.get(Points.END).get(-2));
            tempList4.sort();
            //左边竖
            strokes.get(4).addList(contours.getMatchContour(
                    tempList2.get(0), tempList4.get(0)), true);
            //右边竖
            strokes.get(5).addList(contours.getMatchContour(
                    tempList2.get(2), tempList4.get(1)), true);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
