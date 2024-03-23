package com.bupt.evaluate.processor.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.processor.SpecificExtractor;

//之
public class U4E4B implements SpecificExtractor {

    public static final int strokeNum = 4;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new PointList());
        }
        try {
            //从左向右排序
            points.get(Points.END).sort();
            //横
            strokes.get(0).add(points.get(Points.END).get(0));
            strokes.get(0).add(points.get(Points.INTER).get(1));
            strokes.get(0).add(points.get(Points.END).get(-1));
            //中间竖
            strokes.get(1).add(points.get(Points.END).get(2));
            strokes.get(1).add(points.get(Points.INTER).get(0));
            strokes.get(1).add(points.get(Points.INTER).get(1));
            //左边竖
            strokes.get(2).addAll(contours.findMatchContour(
                    points.get(Points.END).get(1), points.get(Points.INTER).get(0)));
            //右边竖
            strokes.get(3).addAll(contours.findMatchContour(
                    points.get(Points.END).get(3), points.get(Points.INTER).get(0)));
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
