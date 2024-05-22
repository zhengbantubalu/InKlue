package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;

//不 峄山碑
public class U4E0D0000 implements SpecificExtractor {

    public static final int strokeNum = 6;

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
            strokes.get(0).addList(contours.getMatchContour(
                    points.end.get(0), points.end.get(-1)), true);
            //左下半边口
            strokes.get(1).addList(contours.getMatchContour(
                    points.inter.get(0), points.inter.get(2)), false);
            //右下半边口
            strokes.get(2).addList(contours.getMatchContour(
                    points.inter.get(-1), points.inter.get(2)), false);
            //从上向下排序
            points.inter.sort(1, true);
            //中间竖
            strokes.get(3).add(points.inter.get(2));
            strokes.get(3).addList(contours.getMatchContour(
                    points.inter.get(-1), points.end.get(2)), true);
            //左边竖
            strokes.get(4).addList(contours.getMatchContour(
                    points.inter.get(-1), points.end.get(1)), false);
            //右边竖
            strokes.get(5).addList(contours.getMatchContour(
                    points.inter.get(-1), points.end.get(3)), false);
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}
