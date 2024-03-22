package com.bupt.evaluate.processor.extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Strokes;

//山
public class U5C71 implements Extractor {
    public static int strokeNum = 6;

    public void extract(Strokes strokes, Contours contours, Points points) {
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new PointList());
        }
        try {
            //中间竖
            strokes.get(0).add(points.get(Points.END).get(0));
            strokes.get(0).add(points.get(Points.INTER).get(0));
            //分辨两个竖的端点
            PointList tempList = new PointList();
            tempList.add(points.get(Points.END).get(1));
            tempList.add(points.get(Points.END).get(2));
            tempList.sort(0, true);
            //底部横
            strokes.get(4).add(points.get(Points.INTER).get(1));
            strokes.get(4).add(points.get(Points.INTER).get(2));
            strokes.get(4).sort(0, true);
            //左边竖
            strokes.get(1).add(tempList.get(0));
            strokes.get(1).add(strokes.get(4).get(0));
            //右边竖
            strokes.get(2).add(tempList.get(1));
            strokes.get(2).add(strokes.get(4).get(1));
            //左边斜竖
            strokes.get(3).add(points.get(Points.INTER).get(0));
            strokes.get(3).add(strokes.get(4).get(0));
            //右边斜竖
            strokes.get(4).add(points.get(Points.INTER).get(0));
            strokes.get(4).add(strokes.get(4).get(1));
        } catch (NullPointerException ignored) {
        }
    }
}
