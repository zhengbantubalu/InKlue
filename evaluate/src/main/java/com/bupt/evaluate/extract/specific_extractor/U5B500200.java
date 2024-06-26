package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;
import com.bupt.evaluate.util.Constants;

//子 曹全碑
public class U5B500200 implements SpecificExtractor {

    public static final int strokeNum = 3;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //横折
            PointEx pointEx1 = contours.getNearestPoint(
                    new PointEx(Constants.IMAGE_SIZE,0 ));
            strokes.get(0).addList(contours.getMatchContour(
                    points.end.get(0), pointEx1), false);
            strokes.get(0).addList(contours.getMatchContour(
                    pointEx1, points.inter.get(-1)));
            //   丿
            points.end.sort(0, true);
            strokes.get(1).add(points.end.get(3));
            strokes.get(1).addList(contours.getMatchContour(
                    points.inter.get(-1), points.end.get(0)));
            //最后一横
            strokes.get(2).addList(contours.getMatchContour(
                    points.end.get(1), points.inter.get(-1)), true);
            strokes.get(2).addList(contours.getMatchContour(
                    points.inter.get(-1), points.end.get(-1)));
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
