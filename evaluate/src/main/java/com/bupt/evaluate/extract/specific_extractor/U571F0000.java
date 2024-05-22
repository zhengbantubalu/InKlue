package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//土 峄山碑
public class U571F0000 implements SpecificExtractor {

    public static final int strokeNum = 3;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //第一横
            strokes.get(0).add(points.end.get(1));
            strokes.get(0).add(points.end.get(2));
            strokes.get(0).add(points.inter.get(0));
            strokes.get(0).sort();
            strokes.get(0).isStraight = true;
            //第二横
            PointList tempList = new PointList();
            tempList.add(points.end.get(-1));
            tempList.add(points.end.get(-2));
            tempList.sort();
            strokes.get(1).addList(contours.getMatchContour(
                    tempList.get(0), tempList.get(1)), true);
            //竖
            strokes.get(2).addList(contours.getMatchContour(
                    points.end.get(0), points.inter.get(0)), true);
            strokes.get(2).addList(contours.getMatchContour(
                    points.inter.get(0), points.inter.get(1)));
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
