package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//之 九成宫醴泉铭
public class U4E4B0100 implements SpecificExtractor {

    public static final int strokeNum = 4;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //点
            PointList tempList1 = contours.getMatchContour(
                    points.end.get(0), points.end.get(1));
            if (!tempList1.isEmpty()) {
                strokes.get(0).addList(tempList1, true);
            } else {
                strokes.get(0).addList(contours.getMatchContour(
                        points.end.get(0), points.end.get(2)), true);
            }
            //从左向右排序
            points.end.sort();
            //横
            PointList tempList2 = contours.getMatchContour(
                    points.end.get(1), points.end.get(1));
            if (!tempList2.isEmpty()) {
                strokes.get(1).addList(tempList2, true);
            } else {
                strokes.get(1).addList(contours.getMatchContour(
                        points.end.get(1), points.end.get(3)), true);
            }
            strokes.get(1).addList(contours.getMatchContour(
                    points.end.get(1), points.end.get(2)), false);
            //撇
            strokes.get(2).addList(contours.getMatchContour(
                    points.end.get(5), points.inter.get(0)), false);
            //捺
            strokes.get(3).addList(contours.getMatchContour(
                    points.end.get(0), points.end.get(-1)), false);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
