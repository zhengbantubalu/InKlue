package com.bupt.evaluate.data;

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

    //取得笔画长度
    public int getLength() {
        int length = 0;
        for (int i = 0; i < this.size() - 1; i++) {
            length += this.get(i).getDistance(this.get(i + 1));
        }
        return length;
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
