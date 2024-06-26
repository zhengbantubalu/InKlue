package com.bupt.evaluate.extract.specific_extractor;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.extract.SpecificExtractor;
import com.bupt.evaluate.util.Constants;

//中 曹全碑
public class U4E2D0200 implements SpecificExtractor {

    public static final int strokeNum = 5;

    public Strokes extractStrokes(Contours contours, Points points) {
        Strokes strokes = new Strokes();
        for (int i = 0; i < strokeNum; i++) {
            strokes.add(new Stroke());
        }
        try {
            //第一竖
            PointEx pointEx1 = contours.getNearestPoint(
                    new PointEx(0,0 ));
            PointEx pointEx2 = contours.getNearestPoint(
                    new PointEx(0,Constants.IMAGE_SIZE ));
            PointEx pointEx3 = contours.getNearestPoint(
                    new PointEx(Constants.IMAGE_SIZE,0 ));
            PointEx pointEx4 = contours.getNearestPoint(
                    new PointEx(Constants.IMAGE_SIZE,Constants.IMAGE_SIZE ));
            PointList tempList1 = new PointList();
            tempList1.add(points.end.get(2));
            tempList1.add(points.end.get(1));
            tempList1.sort(0,true);
            strokes.get(0).addList(contours.getMatchContour(
                    pointEx1, pointEx2), true);
            //第二横
            PointList tempList_inter_up = new PointList();
            tempList_inter_up.add(points.inter.get(0));
            tempList_inter_up.add(points.inter.get(1));
            tempList_inter_up.add(points.inter.get(2));
            tempList_inter_up.sort(0,true);
            strokes.get(1).addList(contours.getMatchContour(
                    tempList_inter_up.get(0), tempList_inter_up.get(1)), true);
            strokes.get(1).addList(contours.getMatchContour(
                    tempList_inter_up.get(1), tempList_inter_up.get(2)));
            //第三竖
            PointList tempList_inter_low = new PointList();
            tempList_inter_low.add(points.inter.get(-1));
            tempList_inter_low.add(points.inter.get(-2));
            tempList_inter_low.add(points.inter.get(-3));
            tempList_inter_low.sort(0,true);
            strokes.get(2).addList(contours.getMatchContour(
                    pointEx3, pointEx4), true);
            //第四横
            strokes.get(3).addList(contours.getMatchContour(
                    tempList_inter_low.get(0), tempList_inter_low.get(1)), true);
            strokes.get(3).addList(contours.getMatchContour(
                    tempList_inter_low.get(1),pointEx4));
            //最后一竖
            strokes.get(4).add(points.end.get(0));
            strokes.get(4).add(tempList_inter_up.get(1));
            strokes.get(4).add(tempList_inter_low.get(1));
            strokes.get(4).add(points.end.get(-1));
        } catch (NullPointerException ignored) {
        }
        return strokes;
    }
}

