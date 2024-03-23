package com.bupt.evaluate.processor.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.processor.SpecificExtractor;
import com.bupt.evaluate.util.Constants;

//日
public class U65E5 implements SpecificExtractor {

    public static final int strokeNum = 5;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new PointList());
        }
        try {
            //四个角点
            PointEx pointEx1 = contours.findNearestPoint(
                    new PointEx(0, 0));
            PointEx pointEx2 = contours.findNearestPoint(
                    new PointEx(Constants.IMAGE_SIZE, 0));
            PointEx pointEx3 = contours.findNearestPoint(
                    new PointEx(0, Constants.IMAGE_SIZE));
            PointEx pointEx4 = contours.findNearestPoint(
                    new PointEx(Constants.IMAGE_SIZE, Constants.IMAGE_SIZE));
            //顶部横
            strokes.get(0).addAll(contours.findMatchContour(
                    pointEx1, pointEx2));
            //底部横
            strokes.get(1).addAll(contours.findMatchContour(
                    pointEx3, pointEx4));
            //左边竖
            strokes.get(2).addAll(contours.findMatchContour(
                    pointEx1, pointEx3));
            //右边竖
            strokes.get(3).addAll(contours.findMatchContour(
                    pointEx2, pointEx4));
            //中间横
            strokes.get(4).add(points.get(Points.END).get(0));
            strokes.get(4).add(points.get(Points.END).get(1));
            strokes.get(4).sort();
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
