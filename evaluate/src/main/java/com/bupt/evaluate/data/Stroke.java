package com.bupt.evaluate.data;

import com.bupt.evaluate.util.ImageDrawer;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

//一个笔画
public class Stroke extends PointList {

    //取得笔画的笔直程度
    public int getStraightness() {
        Mat points = new Mat(this.size(), 1, CvType.CV_16SC2);
        for (int i = 0; i < this.size(); i++) {
            points.put(i, 0, this.get(i).x, this.get(i).y);
        }
        Mat line = new Mat();
        //直线拟合
        Imgproc.fitLine(points, line, Imgproc.DIST_L2, 0, 0.01, 0.01);
        //求点集到直线的离散程度
        double vx = line.get(0, 0)[0];//x方向的单位向量
        double vy = line.get(1, 0)[0];//y方向的单位向量
        double x0 = line.get(2, 0)[0];//直线上一点的x坐标
        double y0 = line.get(3, 0)[0];//直线上一点的y坐标
        double[] distances = new double[this.size()];
        double sum = 0;
        for (int i = 0; i < this.size(); i++) {
            PointEx p = this.get(i);
            distances[i] = Math.abs(vy * p.x - vx * p.y + vy * x0 - vx * y0) /
                    Math.sqrt(vx * vx + vy * vy);
            sum += distances[i];
        }
        double meanDistance = sum / this.size();
        double sumSquares = 0;
        for (int i = 0; i < this.size(); i++) {
            sumSquares += (distances[i] - meanDistance) * (distances[i] - meanDistance);
        }
        double stdDistance = Math.sqrt(sumSquares / this.size());
        return (int) (100 - (stdDistance / 30 * 100));
    }

    //绘制拟合直线
    public void drawFitLine(Mat img) {
        Mat points = new Mat(this.size(), 1, CvType.CV_16SC2);
        for (int i = 0; i < this.size(); i++) {
            points.put(i, 0, this.get(i).x, this.get(i).y);
        }
        Mat line = new Mat();
        //直线拟合
        Imgproc.fitLine(points, line, Imgproc.DIST_L2, 0, 0.01, 0.01);
        //绘制直线
        ImageDrawer.drawLine(img, line);
    }
}
