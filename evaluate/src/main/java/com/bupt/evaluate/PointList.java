package com.bupt.evaluate;

import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//点列表，每个元素是一个PointEx类
public class PointList extends ArrayList<PointEx> {

    public PointList() {
    }

    //将OpenCV的List<Point>转为本类
    public PointList(List<Point> points) {
        for (Point point : points) {
            this.add(new PointEx(point));
        }
    }

    //去除当前列表中的重复点，若两点横纵坐标差值均小于等于maxDistance则随机删除一个
    //实际上是复制一份PointList，将当前的PointList清空，再将不重复的点加入进去
    public void dropDuplicates(int maxDistance) {
        PointList cloneList = (PointList) this.clone();
        this.clear();
        for (PointEx pointEx : cloneList) {
            if (!this.has(pointEx, maxDistance)) {
                this.add(pointEx);
            }
        }
    }

    //重写ArrayList的get方法，使其可以接受负数索引
    public PointEx get(int index) {
        if (index >= 0) {
            return super.get(index);
        } else {
            return super.get(this.size() + index);
        }
    }

    //搜索当前列表是否已经含有某个点
    //两点横纵坐标差值均小于等于maxDistance则认为是同一点
    public boolean has(PointEx pointEx, int maxDistance) {
        for (PointEx p : this) {
            if (p.equals(pointEx, maxDistance)) {
                return true;
            }
        }
        return false;
    }

    //重载ArrayList的sort方法
    //index=0，按x坐标排序
    //index=1，按y坐标排序
    //isUp=true，升序
    //isUp=false，降序
    public void sort(int index, boolean isUp) {
        int size = this.size();
        for (int i = size - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (isUp == this.get(i).get(index) < this.get(j).get(index)) {
                    Collections.swap(this, i, j);
                }
            }
        }
    }
}
