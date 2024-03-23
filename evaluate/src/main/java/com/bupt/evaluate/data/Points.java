package com.bupt.evaluate.data;

import com.bupt.evaluate.util.Constants;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

//一个汉字的所有特征点，每个元素是一种点的列表，每一个点是一个PointEx类
public class Points extends HashMap<Integer, PointList> {

    //点列表类型对应的索引
    public static final int END = 0;
    public static final int INTER = 1;

    //初始化点列表
    public Points() {
        this.put(0, new PointList());
        this.put(1, new PointList());
    }

    //分别去除每个列表中的重复点，若两点横纵坐标差值均小于等于maxDistance则随机删除一个
    public void dropDuplicates(int maxDistance) {
        for (int key : this.keySet()) {
            PointList thisList = this.get(key);
            if (thisList != null && !thisList.isEmpty()) {
                thisList.dropDuplicates(maxDistance);
            }
        }
    }

    public void dropDuplicates() {
        dropDuplicates(Constants.MAX_DISTANCE);
    }

    //搜索指定的列表是否已经含有某个点，传入-1则搜索全部点
    //两点横纵坐标差值均小于等于maxDistance则认为是同一点，要求两点坐标完全相同需传入0
    public boolean has(int key, PointEx pointEx, int maxDistance) {
        Set<Integer> range;
        if (key == -1) {
            range = this.keySet();
        } else {
            range = new HashSet<>();
            range.add(key);
        }
        for (int k : range) {
            PointList thisList = this.get(k);
            if (thisList != null && !thisList.isEmpty()) {
                if (thisList.has(pointEx, maxDistance)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean has(int key, PointEx pointEx) {
        return this.has(key, pointEx, Constants.MAX_DISTANCE);
    }
}
