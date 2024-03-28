package com.bupt.evaluate.data;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

//一个笔画
public class Stroke extends PointList {

    public Line line;//经过拟合的直线
    public Curve curve;//经过插值的曲线

    //当前笔画是否是直线
    public boolean isStraight = false;

    //向当前笔画的末尾添加点列表，并设置当前笔画是否是直线
    public void addList(PointList pointList, boolean isStraight) {
        this.addAll(pointList);
        this.isStraight = isStraight;
    }

    //不输入isStraight，则不会改变它的值
    public void addList(PointList pointList) {
        this.addAll(pointList);
    }

    //取得笔画长度
    public int getLength() {
        int length = 0;
        for (int i = 0; i < this.size() - 1; i++) {
            length += this.get(i).getDistance(this.get(i + 1));
        }
        return length;
    }

    //取得笔画的四分之一点
    //需要经过直线拟合或曲线插值，若二者都为空则返回(0,0)
    public PointEx getQuartPoint() {
        if (this.isStraight && line != null) {
            return line.getQuartPoint();
        } else if (curve != null) {
            return curve.getQuartPoint();
        }
        return new PointEx(0, 0);
    }

    //直线拟合
    public void fitLine() {
        Mat points = new Mat(this.size(), 1, CvType.CV_16SC2);
        for (int i = 0; i < this.size(); i++) {
            points.put(i, 0, this.get(i).x, this.get(i).y);
        }
        Mat line = new Mat();
        Imgproc.fitLine(points, line, Imgproc.DIST_L2, 0, 0.01, 0.01);
        this.line = new Line(line);
        this.line.setEndpoints(this);
    }

    //曲线插值
    public void interpolateCurve(int size) {
        int stepSize = this.getLength() / size;
        PointEx currentPoint = this.get(0);
        this.curve = new Curve();
        this.curve.add(this.get(0));
        int[] index = new int[]{0};
        for (int i = 1; i <= size; i++) {
            PointEx nextPoint = this.getNextPoint(currentPoint, index, stepSize);
            this.curve.add(nextPoint);
            currentPoint = nextPoint;
        }
        this.curve.add(this.get(-1));
    }

    //取得指定点沿笔画向后移动指定步长后的点
    public PointEx getNextPoint(PointEx currentPoint, int[] index, int stepSize) {
        PointEx nextNode = this.get(index[0] + 1);
        int distance = currentPoint.getDistance(nextNode);
        while (distance < stepSize) {
            stepSize -= distance;
            index[0]++;
            currentPoint = nextNode;
            nextNode = this.get(index[0] + 1);
            distance = currentPoint.getDistance(nextNode);
        }
        int x = (int) (currentPoint.x + (nextNode.x - currentPoint.x) * stepSize / distance);
        int y = (int) (currentPoint.y + (nextNode.y - currentPoint.y) * stepSize / distance);
        return new PointEx(x, y);
    }
}
