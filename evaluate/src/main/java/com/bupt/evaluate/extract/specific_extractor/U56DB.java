package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;
import com.bupt.evaluate.util.Constants;

//四
public class U56DB implements SpecificExtractor {

    public static final int strokeNum = 6;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //从左向右排序
            points.get(Points.INTER).sort();
            //四个角点
            PointEx pointEx1 = contours.getNearestPoint(
                    new PointEx(0, 0));
            PointEx pointEx2 = contours.getNearestPoint(
                    new PointEx(Constants.IMAGE_SIZE, 0));
            PointEx pointEx3 = contours.getNearestPoint(
                    new PointEx(0, Constants.IMAGE_SIZE));
            PointEx pointEx4 = contours.getNearestPoint(
                    new PointEx(Constants.IMAGE_SIZE, Constants.IMAGE_SIZE));
            //顶部横
            strokes.get(0).addList(contours.getMatchContour(pointEx1, pointEx2), false);
            //底部横
            strokes.get(1).addList(contours.getMatchContour(pointEx3, pointEx4), false);
            //左边竖
            strokes.get(2).addList(contours.getMatchContour(pointEx1, pointEx3), false);
            //右边竖
            strokes.get(3).addList(contours.getMatchContour(pointEx2, pointEx4), false);
            //左右撇捺的途径点
            PointEx pointEx5 = contours.getNearestPoint(
                    new PointEx((int) (Constants.IMAGE_SIZE * 0.4), Constants.IMAGE_SIZE / 2));
            PointEx pointEx6 = contours.getNearestPoint(
                    new PointEx((int) (Constants.IMAGE_SIZE * 0.6), Constants.IMAGE_SIZE / 2));
            //中间撇
            strokes.get(4).addList(contours.getMatchContour(
                    points.get(Points.INTER).get(1), pointEx5), false);
            strokes.get(4).addList(contours.getMatchContour(
                    pointEx5, points.get(Points.INTER).get(0)));
            //中间捺
            strokes.get(5).addList(contours.getMatchContour(
                    points.get(Points.INTER).get(2), pointEx6), false);
            strokes.get(5).addList(contours.getMatchContour(
                    pointEx6, points.get(Points.INTER).get(3)));
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
