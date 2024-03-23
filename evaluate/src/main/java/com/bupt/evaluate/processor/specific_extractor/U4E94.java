package com.bupt.evaluate.processor.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.processor.SpecificExtractor;

//五
public class U4E94 implements SpecificExtractor {

    public static final int strokeNum = 4;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new PointList());
        }
        try {
            //第一横
            strokes.get(0).add(points.get(Points.END).get(0));
            strokes.get(0).add(points.get(Points.END).get(1));
            strokes.get(0).add(points.get(Points.INTER).get(0));
            strokes.get(0).add(points.get(Points.INTER).get(1));
            strokes.get(0).sort();
            //第二横
            strokes.get(1).add(points.get(Points.END).get(-1));
            strokes.get(1).add(points.get(Points.END).get(-2));
            strokes.get(1).add(points.get(Points.INTER).get(-1));
            strokes.get(1).add(points.get(Points.INTER).get(-2));
            strokes.get(1).sort();
            //辨别中间叉的四个角点
            PointList tempList1 = new PointList();
            tempList1.add(points.get(Points.INTER).get(0));
            tempList1.add(points.get(Points.INTER).get(1));
            tempList1.sort();
            PointList tempList2 = new PointList();
            tempList2.add(points.get(Points.INTER).get(-1));
            tempList2.add(points.get(Points.INTER).get(-2));
            tempList2.sort();
            //右上到左下
            strokes.get(2).addAll(contours.findMatchContour(
                    tempList1.get(1), points.get(Points.INTER).get(2)));
            strokes.get(2).addAll(contours.findMatchContour(
                    points.get(Points.INTER).get(-3), tempList2.get(0)));
            //左上到右下
            strokes.get(3).addAll(contours.findMatchContour(
                    tempList1.get(0), points.get(Points.INTER).get(2)));
            strokes.get(3).addAll(contours.findMatchContour(
                    points.get(Points.INTER).get(-3), tempList2.get(1)));
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
