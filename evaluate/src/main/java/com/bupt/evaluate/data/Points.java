package com.bupt.evaluate.data;

import com.bupt.evaluate.extract.PointExtractor;
import com.bupt.evaluate.util.Constants;

import org.opencv.core.Mat;

//一个汉字的所有特征点
public class Points {

    public PointList end;//端点列表
    public PointList inter;//交点列表

    //调用特征点提取器，从细化图像中提取笔画的端点和交点
    public static Points mat2Points(Mat img) {
        return PointExtractor.mat2Points(img);
    }

    //初始化点列表
    public Points() {
        this.end = new PointList();
        this.inter = new PointList();
    }

    //去除重复点，包括一类点中的重复点和两类点间的重复点(如毛刺)
    //若两点横纵坐标差值均小于等于maxDistance则随机删除一个
    public void dropDuplicates(int maxDistance) {
        //首先去除交点中的重复点
        inter.dropDuplicates(maxDistance);
        PointList cloneEnd = (PointList) end.clone();
        end.clear();
        for (PointEx endPoint : cloneEnd) {
            //如果存在与端点相近(以标准距离的2倍为半径)的交点，则需要去毛刺
            if (inter.has(endPoint, maxDistance * 2)) {
                inter.remove(inter.getIndex(endPoint, maxDistance * 2));
                //如果一个交点对应多个端点，说明是笔画末尾的毛刺，保留一个端点
                if (cloneEnd.getIndexList(endPoint).size() > 1) {
                    end.add(endPoint);
                }
            } else {
                if (!end.has(endPoint, maxDistance)) {
                    end.add(endPoint);
                }
            }
        }
    }

    public void dropDuplicates() {
        dropDuplicates(Constants.MAX_DISTANCE);
    }
}
