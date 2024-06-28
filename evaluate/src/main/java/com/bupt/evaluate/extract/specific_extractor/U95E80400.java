package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;
import com.bupt.evaluate.util.Constants;

//门 圣教序
public class U95E80400 implements SpecificExtractor {

    public static final int strokeNum = 4;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //竖
            points.end.sort();
            PointList tempList1 = new PointList();
            tempList1.add(points.end.get(0));
            tempList1.add(points.end.get(1));
            strokes.get(0).addList(contours.getMatchContour(
                    tempList1.get(0), tempList1.get(1)), true);
            //点
            PointList tempList2 = new PointList();
            tempList2.add(points.end.get(2));
            tempList2.add(points.end.get(3));
            strokes.get(1).addList(contours.getMatchContour(
                    tempList2.get(0), tempList2.get(1)), false);
            //横折
            PointEx pointEx2 = contours.getNearestPoint(
                    new PointEx(Constants.IMAGE_SIZE, 0));
            PointEx pointEx4 = contours.getNearestPoint(
                    new PointEx(Constants.IMAGE_SIZE, Constants.IMAGE_SIZE));
            strokes.get(2).addList(contours.getMatchContour(
                    points.end.get(4), pointEx2), true);
            strokes.get(3).addList(contours.getMatchContour(
                    pointEx2, pointEx4), false);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
