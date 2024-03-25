package com.bupt.evaluate.data;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

//一个笔画
public class Stroke extends PointList {

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

    //直线拟合
    public Line fitLine() {
        Mat points = new Mat(this.size(), 1, CvType.CV_16SC2);
        for (int i = 0; i < this.size(); i++) {
            points.put(i, 0, this.get(i).x, this.get(i).y);
        }
        Mat line = new Mat();
        Imgproc.fitLine(points, line, Imgproc.DIST_L2, 0, 0.01, 0.01);
        Line retLine = new Line(line);
        retLine.setEndpoints(this);
        return retLine;
    }
}
