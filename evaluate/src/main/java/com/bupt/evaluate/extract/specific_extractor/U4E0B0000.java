package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//下 峄山碑
public class U4E0B0000 implements SpecificExtractor {

    public static final int strokeNum = 3;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //第一横
            PointList tempList = new PointList();
            tempList.add(points.end.get(0));
            tempList.add(points.end.get(1));
            tempList.sort();
            strokes.get(0).addList(contours.getMatchContour(
                    tempList.get(0), tempList.get(1)), true);
            //第二横
            strokes.get(1).addList(contours.getMatchContour(
                    points.inter.get(1), points.end.get(2)), true);
            //竖
            strokes.get(2).addList(contours.getMatchContour(
                    points.inter.get(0), points.end.get(3)), false);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
