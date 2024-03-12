package com.bupt.evaluate.extractors;

import com.bupt.evaluate.Contours;
import com.bupt.evaluate.Extractor;
import com.bupt.evaluate.PointList;
import com.bupt.evaluate.Points;
import com.bupt.evaluate.Strokes;

//四
public class U56DB implements Extractor {
    public static int strokeNum = 6;

    public void extract(Strokes strokes, Contours contours, Points points) {
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new PointList());
        }
        try {
            //分辨上面两个交点
            PointList tempList1 = new PointList();
            tempList1.add(points.get(Points.INTER).get(0));
            tempList1.add(points.get(Points.INTER).get(1));
            tempList1.sort(0, true);
            //分辨下面两个交点
            PointList tempList2 = new PointList();
            tempList2.add(points.get(Points.INTER).get(2));
            tempList2.add(points.get(Points.INTER).get(3));
            tempList2.sort(0, true);
            //中间左边竖
            strokes.get(0).addAll(contours.findMatchContour(
                    tempList1.get(0), tempList2.get(0)));
            //中间右边竖
            strokes.get(1).addAll(contours.findMatchContour(
                    tempList1.get(1), tempList2.get(1)));
        } catch (NullPointerException ignored) {
        }
    }
}
