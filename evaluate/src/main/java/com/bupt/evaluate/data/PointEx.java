package com.bupt.evaluate.data;

import com.bupt.evaluate.util.Constants;

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
        this.x = point.x;
        this.y = point.y;
    }

    //取得对应索引的坐标值
    public double get(int index) {
        if (index == 0) {
            return this.x;
        } else {
            return this.y;
        }
    }

    //取得该点与另一点的欧氏距离
    public int getDistance(PointEx pointEx) {
        return (int) Math.sqrt(Math.pow(this.x - pointEx.x, 2) + Math.pow(this.y - pointEx.y, 2));
    }

    //判断该点与另一点的横纵坐标差值是否均小于等于maxDistance
    public boolean equals(PointEx pointEx, int maxDistance) {
        return (Math.abs(this.x - pointEx.x) <= maxDistance &&
                Math.abs(this.y - pointEx.y) <= maxDistance);
    }

    public boolean equals(PointEx pointEx) {
        return this.equals(pointEx, Constants.MAX_DISTANCE);
    }
}
