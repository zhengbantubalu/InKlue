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
        //首先简单去除交点中的重复点
        inter.dropDuplicates(maxDistance);
        PointList cloneInter = (PointList) inter.clone();
        int interSize = inter.size();
        inter.clear();
        for (; interSize > 0; interSize--) {
            PointEx interPoint = cloneInter.get(0);
            cloneInter.remove(0);
            //如果存在两个相近的交点，则需要消歧，方法为取两点的中点
            if (cloneInter.has(interPoint, maxDistance * 2)) {
                int sameIndex = cloneInter.getIndex(interPoint, maxDistance * 2);
                PointEx samePoint = cloneInter.get(sameIndex);
                cloneInter.remove(sameIndex);
                interSize--;
                PointEx resultPoint = new PointEx(
                        (int) (interPoint.x + samePoint.x) / 2,
                        (int) (interPoint.y + samePoint.y) / 2);
                inter.add(resultPoint);
            } else {
                inter.add(interPoint);
            }
        }
        //去除端点中的重复点
        PointList cloneEnd = (PointList) end.clone();
        end.clear();
        for (PointEx endPoint : cloneEnd) {
            //如果存在与端点相近的交点，则需要去毛刺
            if (inter.has(endPoint, maxDistance * 3 / 2)) {
                inter.remove(inter.getIndex(endPoint, maxDistance * 3 / 2));
                //如果一个交点对应多个端点，说明是笔画末尾的毛刺，保留一个端点
                if (cloneEnd.getIndexList(endPoint).size() > 1) {
                    end.add(endPoint);
                }
            } else if (!end.has(endPoint, maxDistance)) {
                end.add(endPoint);
            }
        }
    }

    public void dropDuplicates() {
        dropDuplicates(Constants.MAX_DISTANCE);
    }
}
