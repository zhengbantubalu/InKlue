package com.bupt.evaluate;

//点，继承自OpenCV的Point类
public class Point extends org.opencv.core.Point {

    //重载构造方法，允许整数输入
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //取得对应索引的坐标值
    public double get(int index) {
        if (index == 0) {
            return this.x;
        } else {
            return this.y;
        }
    }

    //判断该点与另一点的横纵坐标差值是否均小于等于maxDistance
    //如需判断是否完全相等，建议使用父类的equals方法
    public boolean equals(Point point, int maxDistance) {
        return (Math.abs(this.x - point.x) <= maxDistance &&
                Math.abs(this.y - point.y) <= maxDistance);
    }
}
