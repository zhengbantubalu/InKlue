package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;
import com.bupt.evaluate.util.Constants;

//石 峄山碑
public class U77F30000 implements SpecificExtractor {

    public static final int strokeNum = 5;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //从左向右排序
            points.end.sort();
            points.inter.sort();
            //横撇拐点
            PointEx pointEx1 = contours.getNearestPoint(
                    new PointEx(0, 0));
            //顶部长横
            strokes.get(0).addList(contours.getMatchContour(
                    pointEx1, points.end.get(3)), true);
            //左侧长撇
            strokes.get(1).addList(contours.getMatchContour(
                    pointEx1, points.end.get(0)), false);
            //口字中间短横
            strokes.get(2).add(points.inter.get(0));
            strokes.get(2).add(points.inter.get(1));
            strokes.get(2).isStraight = true;
            //口字底部横途径点
            PointEx pointEx2 = contours.getNearestPoint(
                    new PointEx((int) (Constants.IMAGE_SIZE * 0.6), Constants.IMAGE_SIZE / 2));
            //口字左半
            strokes.get(3).addList(contours.getMatchContour(
                    points.end.get(1), pointEx2), false);
            //口字右半
            strokes.get(4).addList(contours.getMatchContour(
                    points.end.get(2), pointEx2), false);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
