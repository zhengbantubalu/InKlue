package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;
import com.bupt.evaluate.util.Constants;

//之 曹全碑
public class U4E4B0200 implements SpecificExtractor {

    public static final int strokeNum = 4;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //第一点
            points.end.sort();
            PointEx pointEx =new PointEx((int)points.end.get(-3).x+18,(int)points.end.get(-3).y+18);
            strokes.get(0).add(points.end.get(-3));
            strokes.get(0).add(pointEx);
            strokes.get(0).isStraight=true;
            //第二点
            PointEx pointEx1 = contours.getNearestPoint(
                    new PointEx(0,0 ));
            strokes.get(1).addList(contours.getMatchContour(
                    pointEx1, points.inter.get(0)), true);
            //第三撇
            PointEx pointEx2 = contours.getNearestPoint(
                    new PointEx(0, Constants.IMAGE_SIZE ));
            strokes.get(2).addList(contours.getMatchContour(
                    points.end.get(-2), points.inter.get(0)), false);
            strokes.get(2).addList(contours.getMatchContour(
                    points.inter.get(0), pointEx2));
            //捺
            strokes.get(3).addList(contours.getMatchContour(
                    pointEx2, points.end.get(-1)), false);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}

