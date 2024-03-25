package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;
import com.bupt.evaluate.util.Constants;

//廿
public class U5EFF implements SpecificExtractor {

    public static final int strokeNum = 4;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //从左向右排序
            points.get(Points.END).sort();
            points.get(Points.INTER).sort();
            //第一横
            strokes.get(0).add(points.get(Points.END).get(0));
            strokes.get(0).add(points.get(Points.INTER).get(0));
            strokes.get(0).add(points.get(Points.INTER).get(1));
            strokes.get(0).add(points.get(Points.END).get(3));
            //左下右下转折点
            PointEx pointEx1 = contours.findNearestPoint(
                    new PointEx(0, Constants.IMAGE_SIZE));
            PointEx pointEx2 = contours.findNearestPoint(
                    new PointEx(Constants.IMAGE_SIZE, Constants.IMAGE_SIZE));
            //左边竖
            strokes.get(1).addAll(contours.findMatchContour(
                    points.get(Points.END).get(1), points.get(Points.INTER).get(0)));
            strokes.get(1).addAll(contours.findMatchContour(
                    points.get(Points.INTER).get(0), pointEx1));
            //右边竖
            strokes.get(2).addAll(contours.findMatchContour(
                    points.get(Points.END).get(2), points.get(Points.INTER).get(1)));
            strokes.get(2).addAll(contours.findMatchContour(
                    points.get(Points.INTER).get(1), pointEx2));
            //底部横
            strokes.get(3).addAll(contours.findMatchContour(
                    pointEx1, pointEx2));
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
