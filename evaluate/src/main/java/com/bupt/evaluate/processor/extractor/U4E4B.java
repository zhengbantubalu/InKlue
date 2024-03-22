package com.bupt.evaluate.processor.extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Strokes;

//之
public class U4E4B implements Extractor {
    public static int strokeNum = 4;

    public void extract(Strokes strokes, Contours contours, Points points) {
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new PointList());
        }
        try {
            //横
            strokes.get(0).add(points.get(Points.END).get(-1));
            strokes.get(0).add(points.get(Points.END).get(-2));
            strokes.get(1).add(points.get(Points.INTER).get(1));
            strokes.get(0).sort(0, true);
            //中间竖
            strokes.get(1).add(points.get(Points.END).get(0));
            strokes.get(1).add(points.get(Points.INTER).get(0));
            strokes.get(1).add(points.get(Points.INTER).get(1));
            //分辨两个竖的端点
            PointList tempList = new PointList();
            tempList.add(points.get(Points.END).get(1));
            tempList.add(points.get(Points.END).get(2));
            tempList.sort(0, true);
            //左边竖
            strokes.get(2).addAll(contours.findMatchContour(
                    tempList.get(0), points.get(Points.INTER).get(0)));
            //右边竖
            strokes.get(3).addAll(contours.findMatchContour(
                    tempList.get(1), points.get(Points.INTER).get(0)));
        } catch (NullPointerException ignored) {
        }
    }
}
