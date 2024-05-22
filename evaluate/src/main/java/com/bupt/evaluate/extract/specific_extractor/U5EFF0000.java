package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;
import com.bupt.evaluate.util.Constants;

//廿 峄山碑
public class U5EFF0000 implements SpecificExtractor {

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
            //第一横
            strokes.get(0).add(points.end.get(0));
            strokes.get(0).add(points.inter.get(0));
            strokes.get(0).add(points.inter.get(1));
            strokes.get(0).add(points.end.get(-1));
            strokes.get(0).isStraight = true;
            //左下右下转折点
            PointEx pointEx1 = contours.getNearestPoint(
                    new PointEx(0, Constants.IMAGE_SIZE));
            PointEx pointEx2 = contours.getNearestPoint(
                    new PointEx(Constants.IMAGE_SIZE, Constants.IMAGE_SIZE));
            //左边竖
            strokes.get(1).addList(contours.getMatchContour(
                    points.end.get(1), points.inter.get(0)), true);
            strokes.get(1).addList(contours.getMatchContour(
                    points.inter.get(0), pointEx1));
            //右边竖
            strokes.get(2).addList(contours.getMatchContour(
                    points.end.get(2), points.inter.get(1)), true);
            strokes.get(2).addList(contours.getMatchContour(
                    points.inter.get(1), pointEx2));
            //底部横
            strokes.get(3).addList(contours.getMatchContour(
                    pointEx1, pointEx2), false);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
