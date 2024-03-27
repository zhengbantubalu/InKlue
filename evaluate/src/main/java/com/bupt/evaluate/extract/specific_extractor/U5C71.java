package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//山
public class U5C71 implements SpecificExtractor {

    public static final int strokeNum = 6;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //从左向右排序
            points.end.sort();
            points.inter.sort();
            //中间竖
            strokes.get(0).addList(contours.getMatchContour(
                    points.end.get(1), points.inter.get(1)), true);
            //底部横
            strokes.get(1).addList(contours.getMatchContour(
                    points.inter.get(0), points.inter.get(2)), false);
            //左边竖
            strokes.get(2).addList(contours.getMatchContour(
                    points.end.get(0), points.inter.get(0)), false);
            //右边竖
            strokes.get(3).addList(contours.getMatchContour(
                    points.end.get(2), points.inter.get(2)), false);
            //左边斜竖
            strokes.get(4).addList(contours.getMatchContour(
                    points.inter.get(1), points.inter.get(0)), true);
            //右边斜竖
            strokes.get(5).addList(contours.getMatchContour(
                    points.inter.get(1), points.inter.get(2)), true);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
