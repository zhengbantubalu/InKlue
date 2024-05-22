package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;
import com.bupt.evaluate.util.Constants;

//山 九成宫醴泉铭
public class U5C710100 implements SpecificExtractor {

    public static final int strokeNum = 4;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //从左向右排序
            points.end.sort();
            points.inter.sort();
            //中间竖
            strokes.get(0).addList(contours.getMatchContour(
                    points.end.get(1), points.inter.get(0)), false);
            //左下转折点
            PointEx pointEx = contours.getNearestPoint(
                    new PointEx(0, Constants.IMAGE_SIZE));
            //左边竖
            strokes.get(1).addList(contours.getMatchContour(
                    points.end.get(0), pointEx), false);
            //底部横
            strokes.get(2).addList(contours.getMatchContour(
                    pointEx, points.inter.get(0)), true);
            strokes.get(2).addList(contours.getMatchContour(
                    points.inter.get(0), points.inter.get(-1)));
            //分辨右边竖的上下两点
            PointList tempList = new PointList();
            tempList.add(points.end.get(-1));
            tempList.add(points.end.get(-2));
            tempList.sort(1, true);
            //右边竖
            strokes.get(3).addList(contours.getMatchContour(
                    tempList.get(0), tempList.get(1)), false);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
