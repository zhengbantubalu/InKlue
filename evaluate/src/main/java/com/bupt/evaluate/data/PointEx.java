package com.bupt.evaluate.data;

import org.opencv.core.Point;

//点，继承自OpenCV的Point类
public class PointEx extends Point {

    //重载构造方法，允许整数输入
    public PointEx(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //将OpenCV的Point转为本类
    public PointEx(Point point) {
        x = point.x;
        y = point.y;
    }

    //取得对应索引的坐标值
    public double get(int index) {
        if (index == 0) {
            return x;
        } else {
            return y;
        }
    }

    //判断该点与另一点的横纵坐标差值是否均小于等于maxDistance
    public boolean equals(PointEx pointEx, int maxDistance) {
        return (Math.abs(x - pointEx.x) <= maxDistance &&
                Math.abs(y - pointEx.y) <= maxDistance);
    }
}
