package com.bupt.evaluate.data;

//曲线类，使用步长均匀的点列表描述
public class Curve extends PointList {
    //取得曲线的四分之一点
    public PointEx getQuartPoint() {
        return this.get(this.size() / 4);
    }
}
