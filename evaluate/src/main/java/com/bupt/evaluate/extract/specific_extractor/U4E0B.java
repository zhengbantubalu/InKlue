package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//下
public class U4E0B implements SpecificExtractor {

    public static final int strokeNum = 3;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //第一横
            PointList tempList = new PointList();
            tempList.add(points.get(Points.END).get(0));
            tempList.add(points.get(Points.END).get(1));
            tempList.sort();
            strokes.get(0).addList(contours.getMatchContour(
                    tempList.get(0), tempList.get(1)), true);
            //第二横
            strokes.get(1).addList(contours.getMatchContour(
                    points.get(Points.INTER).get(1), points.get(Points.END).get(2)), true);
            //竖
            strokes.get(2).addList(contours.getMatchContour(
                    points.get(Points.INTER).get(0), points.get(Points.END).get(3)), false);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
