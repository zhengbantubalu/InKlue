package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;
import com.bupt.evaluate.util.Constants;

//也 圣教序
public class U4E5F0400 implements SpecificExtractor {

    public static final int strokeNum = 4;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //分辨两个竖的端点
            PointList tempList = new PointList();
            tempList.add(points.end.get(-2));
            tempList.add(points.end.get(-3));
            tempList.sort();
            //从左向右排序
            points.end.sort();
            points.inter.sort();
            //横折拐点
            PointEx pointEx = contours.getNearestPoint(new PointEx(Constants.IMAGE_SIZE, 0));
            //横折的横
            strokes.get(0).add(points.end.get(0));
            strokes.get(0).add(points.inter.get(0));
            strokes.get(0).add(points.inter.get(1));
            strokes.get(0).add(pointEx);
            strokes.get(0).isStraight = true;
            //横折的竖
            strokes.get(1).add(pointEx);
            strokes.get(1).add(tempList.get(1));
            strokes.get(1).isStraight = true;
            //竖弯钩
            strokes.get(3).add(points.end.get(1));
            strokes.get(3).addList(contours.getMatchContour(
                    points.inter.get(0), points.end.get(-1)), false);
            //从上向下排序
            points.end.sort(1, true);
            //中间竖
            strokes.get(2).add(points.end.get(0));
            strokes.get(2).add(points.inter.get(1));
            strokes.get(2).add(tempList.get(0));
            strokes.get(2).isStraight = true;
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
