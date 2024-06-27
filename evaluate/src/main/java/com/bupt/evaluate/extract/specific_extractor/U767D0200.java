package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;
import com.bupt.evaluate.util.Constants;

//白 曹全碑
public class U767D0200 implements SpecificExtractor {

    public static final int strokeNum = 6;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //第一竖
            strokes.get(0).addList(contours.getMatchContour(
                    points.end.get(0), points.inter.get(0)), true);
            //第二竖
            PointEx pointEx1 = contours.getNearestPoint(
                    new PointEx(0,0 ));
            PointEx pointEx2 = contours.getNearestPoint(
                    new PointEx(0,Constants.IMAGE_SIZE ));
            PointEx pointEx3 = contours.getNearestPoint(
                    new PointEx(Constants.IMAGE_SIZE ,0 ));
            PointEx pointEx4 = contours.getNearestPoint(
                    new PointEx(Constants.IMAGE_SIZE ,Constants.IMAGE_SIZE  ));
            strokes.get(1).addList(contours.getMatchContour(
                    pointEx1, pointEx2), true);
            //第三横
            points.end.sort();
            strokes.get(2).addList(contours.getMatchContour(
                    points.end.get(1), points.inter.get(0)), true);
            strokes.get(2).addList(contours.getMatchContour(
                    points.inter.get(0), pointEx3));
            //第四竖
            strokes.get(3).addList(contours.getMatchContour(
                    pointEx3, pointEx4), true);
            //第五横
            strokes.get(4).addList(contours.getMatchContour(
                    points.end.get(2), points.end.get(4)), true);
            //最后一横
            strokes.get(5).addList(contours.getMatchContour(
                    pointEx2, pointEx4), true);

        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}

