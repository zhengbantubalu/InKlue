package com.bupt.evaluate.data;

//曲线类，使用步长均匀的点列表描述
public class Curve extends PointList {

    public int stepSize;//步长

    public Curve(int stepSize) {
        this.stepSize = stepSize;
    }

    //取得曲线的四分之一点
    public PointEx getQuartPoint() {
        return this.get(this.size() / 4);
    }

    //取得曲线长度
    public int getLength() {
        return stepSize * this.size();
    }
}
