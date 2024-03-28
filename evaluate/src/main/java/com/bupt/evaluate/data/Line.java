package com.bupt.evaluate.data;

import org.opencv.core.Mat;

//直线类，包含多种描述方式
public class Line {

    public PointEx p1;//端点1
    public PointEx p2;//端点2
    public double vx;//直线方向向量x分量
    public double vy;//直线方向向量y分量
    public double x0;//直线上任意一点x坐标
    public double y0;//直线上任意一点y坐标

    //将OpenCV的fitLine直线拟合结果转为本类
    public Line(Mat line) {
        this.vx = line.get(0, 0)[0];
        this.vy = line.get(1, 0)[0];
        this.x0 = line.get(2, 0)[0];
        this.y0 = line.get(3, 0)[0];
    }

    //根据笔画端点设置直线的端点
    //使用需要直线端点的方法前，需要先调用此方法
    public void setEndpoints(Stroke stroke) {
        p1 = getNearestPoint(stroke.get(0));
        p2 = getNearestPoint(stroke.get(stroke.size() - 1));
    }

    //取得直线的长度
    public int getLength() {
        return (int) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    //取得直线的倾斜角，返回值为角度制，范围为0-180
    public double getAngle() {
        double angle = Math.toDegrees(Math.atan(vy / vx));
        if (angle < 0) {
            angle += 180;
        }
        return angle;
    }

    //取得直线的中点
    public PointEx getMidpoint() {
        return new PointEx((int) ((p1.x + p2.x) / 2), (int) ((p1.y + p2.y) / 2));
    }

    //取得直线的四分之一点
    public PointEx getQuartPoint() {
        return new PointEx((int) (p1.x * 0.75 + p2.x * 0.25), (int) (p1.y * 0.75 + p2.y * 0.25));
    }

    //取得点p到直线的距离
    public double getDistance(PointEx p) {
        return Math.abs(vy * p.x - vx * p.y + vy * x0 - vx * y0) / Math.sqrt(vx * vx + vy * vy);
    }

    //取得当前直线与另一条平行直线的距离
    public double getDistance(Line l) {
        return Math.abs((x0 - l.x0) * vy - (y0 - l.y0) * vx) / Math.sqrt(vx * vx + vy * vy);
    }

    //取得直线上距离点p最近的一点
    public PointEx getNearestPoint(PointEx p) {
        double t = ((p.x - x0) * vx + (p.y - y0) * vy) / (vx * vx + vy * vy);
        return new PointEx((int) (x0 + t * vx), (int) (y0 + t * vy));
    }
}
