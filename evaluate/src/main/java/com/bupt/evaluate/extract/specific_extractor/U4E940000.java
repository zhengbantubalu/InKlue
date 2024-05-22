package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//五 峄山碑
public class U4E940000 implements SpecificExtractor {

    public static final int strokeNum = 4;

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
            //第二横
            PointList tempList2 = new PointList();
            tempList2.add(points.end.get(-1));
            tempList2.add(points.end.get(-2));
            tempList2.sort();
            strokes.get(1).addList(contours.getMatchContour(
                    tempList2.get(0), tempList2.get(1)), true);
            //辨别中间叉的四个角点
            PointList tempList3 = new PointList();
            tempList3.add(points.inter.get(0));
            tempList3.add(points.inter.get(1));
            tempList3.sort();
            PointList tempList4 = new PointList();
            tempList4.add(points.inter.get(-1));
            tempList4.add(points.inter.get(-2));
            tempList4.sort();
            //右上到左下
            strokes.get(2).addList(contours.getMatchContour(
                    tempList3.get(1), points.inter.get(2)), true);
            strokes.get(2).addList(contours.getMatchContour(
                    points.inter.get(-3), tempList4.get(0)));
            //左上到右下
            strokes.get(3).addList(contours.getMatchContour(
                    tempList3.get(0), points.inter.get(2)), true);
            strokes.get(3).addList(contours.getMatchContour(
                    points.inter.get(-3), tempList4.get(1)));
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
