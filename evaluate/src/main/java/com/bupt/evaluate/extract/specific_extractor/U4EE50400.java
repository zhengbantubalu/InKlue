package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;
import com.bupt.evaluate.util.Constants;

//以 圣教序
public class U4EE50400 implements SpecificExtractor {

    public static final int strokeNum = 3;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            points.end.sort();
            //第一点
            strokes.get(0).addList(contours.getMatchContour(
                    points.end.get(0), points.end.get(1)), false);
            //第二点
            strokes.get(1).addList(contours.getMatchContour(
                    points.end.get(2), points.end.get(3)), false);
            //第三连笔
            points.end.sort(1,true);
            strokes.get(2).addList(contours.getMatchContour(
                    points.end.get(0), points.end.get(6)), false);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
