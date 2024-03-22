package com.bupt.evaluate.data;

import java.util.ArrayList;
import java.util.Collections;

//一个汉字的轮廓列表
public class Contours extends ArrayList<PointList> {

    //根据起止点查找匹配轮廓，返回一个匹配的轮廓，并正序排列，无匹配则返回空
    public PointList findMatchContour(PointEx start, PointEx end) {
        int matchCode;
        for (PointList contour : this) {
            matchCode = contour.match(start, end, 10);
            if (matchCode == 1) {
                return contour;
            } else if (matchCode == 2) {
                Collections.reverse(contour);
                return contour;
            }
        }
        return null;
    }

    //分割轮廓(旧方法)
    //把轮廓从拐点断开，分割成两段轮廓
    //保留原始轮廓，所有分割出的轮廓加在原始轮廓后面
    public void split() {
        int contourNum = this.size();
        for (int i = 0; i < contourNum; i++) {
            PointList contour = this.get(i);
            for (int j = 1, previous = 0; j < contour.size() - 1; j++) {
                int angle = contour.getAngle(j);
                if (angle < 110 || angle > 250) {
                    PointList sublist = new PointList(contour.subList(previous, j + 1));
                    this.add(sublist);
                    previous = j;
                }
            }
        }
    }
}
