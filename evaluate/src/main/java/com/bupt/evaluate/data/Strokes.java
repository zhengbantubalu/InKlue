package com.bupt.evaluate.data;

import com.bupt.evaluate.util.Constants;

import java.util.ArrayList;

//一个汉字的笔画列表，每个元素是类型为Stroke的一个笔画，每个笔画的元素是PointEx类
public class Strokes extends ArrayList<Stroke> {

    //分别去除每个笔画中的重复点，若两点横纵坐标差值均小于等于maxDistance则随机删除一个
    public void dropDuplicates(int maxDistance) {
        for (Stroke stroke : this) {
            stroke.dropDuplicates(maxDistance);
        }
    }

    public void dropDuplicates() {
        this.dropDuplicates(Constants.MAX_DISTANCE);
    }
}
