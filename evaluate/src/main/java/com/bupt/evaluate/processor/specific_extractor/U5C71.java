package com.bupt.evaluate.processor.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.processor.SpecificExtractor;

//山
public class U5C71 implements SpecificExtractor {

    public static final int strokeNum = 6;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new PointList());
        }
        try {
            //从左向右排序
            points.get(Points.END).sort();
            points.get(Points.INTER).sort();
            //中间竖
            strokes.get(0).add(points.get(Points.END).get(1));
            strokes.get(0).add(points.get(Points.INTER).get(1));
            //底部横
            strokes.get(1).addAll(contours.findMatchContour(
                    points.get(Points.INTER).get(0), points.get(Points.INTER).get(2)));
            //左边竖
            strokes.get(2).addAll(contours.findMatchContour(
                    points.get(Points.END).get(0), points.get(Points.INTER).get(0)));
            //右边竖
            strokes.get(3).addAll(contours.findMatchContour(
                    points.get(Points.END).get(2), points.get(Points.INTER).get(2)));
            //左边斜竖
            strokes.get(4).add(points.get(Points.INTER).get(1));
            strokes.get(4).add(points.get(Points.INTER).get(0));
            //右边斜竖
            strokes.get(5).add(points.get(Points.INTER).get(1));
            strokes.get(5).add(points.get(Points.INTER).get(2));
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
