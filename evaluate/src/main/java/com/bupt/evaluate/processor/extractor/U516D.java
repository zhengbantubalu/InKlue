package com.bupt.evaluate.processor.extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Strokes;

//六
public class U516D implements Extractor {
    public static int strokeNum = 5;

    public void extract(Strokes strokes, Contours contours, Points points) {
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new PointList());
        }
        try {
            //分辨三个交点
            PointList tempList1 = new PointList();
            tempList1.add(points.get(Points.INTER).get(0));
            tempList1.add(points.get(Points.INTER).get(1));
            tempList1.add(points.get(Points.INTER).get(2));
            tempList1.sort(0, true);
            //顶部短竖
            strokes.get(0).add(points.get(Points.END).get(0));
            strokes.get(0).add(tempList1.get(1));
            //分辨两个横的端点
            PointList tempList2 = new PointList();
            tempList2.add(points.get(Points.END).get(1));
            tempList2.add(points.get(Points.END).get(2));
            tempList2.sort(0, true);
            //左半弯横
            strokes.get(1).addAll(contours.findMatchContour(
                    tempList1.get(1), tempList2.get(0)));
            //右半弯横
            strokes.get(2).addAll(contours.findMatchContour(
                    tempList1.get(1), tempList2.get(1)));
            //分辨两个竖的端点
            PointList tempList3 = new PointList();
            tempList3.add(points.get(Points.END).get(-1));
            tempList3.add(points.get(Points.END).get(-2));
            tempList3.sort(0, true);
            //左边弯竖
            strokes.get(3).addAll(contours.findMatchContour(
                    tempList1.get(0), tempList3.get(0)));
            //右边弯竖
            strokes.get(4).addAll(contours.findMatchContour(
                    tempList1.get(2), tempList3.get(1)));
        } catch (NullPointerException ignored) {
        }
    }
}