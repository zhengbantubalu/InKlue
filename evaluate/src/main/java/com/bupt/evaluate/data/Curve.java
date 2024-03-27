package com.bupt.evaluate.data;

//曲线类，使用步长均匀的点列表描述
public class Curve extends PointList {

    //曲线插值
    public static Curve interpolateCurve(Stroke stroke, int size) {
        if (stroke.isEmpty()) {
            stroke.add(new PointEx(0, 0));
            stroke.add(new PointEx(512, 512));
        }
        int stepSize = stroke.getLength() / size;
        Curve curve = new Curve();
        PointEx currentPoint = stroke.get(0);
        curve.add(stroke.get(0));
        int[] index = new int[]{0};
        for (int i = 1; i <= size; i++) {
            PointEx nextPoint = stroke.getNextPoint(currentPoint, index, stepSize);
            curve.add(nextPoint);
            currentPoint = nextPoint;
        }
        curve.add(stroke.get(-1));
        return curve;
    }

    //取得曲线的四分之一点
    public PointEx getQuartPoint() {
        return this.get(this.size() / 4);
    }
}
