package com.bupt.evaluate.data;

import com.bupt.evaluate.util.Constants;

import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//点列表，每个元素是一个PointEx类
public class PointList extends ArrayList<PointEx> {

    //空构造方法，用于创建空列表
    public PointList() {
    }

    //将OpenCV的List<Point>转为本类
    public PointList(List<? extends Point> points) {
        for (Point point : points) {
            this.add(new PointEx(point));
        }
    }

    //重写ArrayList的get方法，使其可以接受负数和超出范围的索引
    //如果产生错误，则返回(0,0)点
    public PointEx get(int index) {
        try {
            if (index >= 0) {
                return super.get(index % this.size());
            } else {
                return super.get(index + this.size());
            }
        } catch (IndexOutOfBoundsException | ArithmeticException ignored) {
            return new PointEx(0, 0);
        }
    }

    //重载ArrayList的sort方法
    //index=0，按x坐标排序
    //index=1，按y坐标排序
    //isUp=true，升序
    //isUp=false，降序
    public void sort(int index, boolean isUp) {
        int size = this.size();
        for (int i = size - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (isUp == this.get(i).get(index) < this.get(j).get(index)) {
                    Collections.swap(this, i, j);
                }
            }
        }
    }

    //默认排序方法采用按x坐标升序排序
    public void sort() {
        this.sort(0, true);
    }

    //搜索当前列表是否已经含有某个点
    //两点横纵坐标差值均小于等于maxDistance则认为是同一点
    public boolean has(PointEx pointEx, int maxDistance) {
        for (PointEx p : this) {
            if (p.equals(pointEx, maxDistance)) {
                return true;
            }
        }
        return false;
    }

    //搜索当前列表中某个点的索引，不存在则返回-1
    //两点横纵坐标差值均小于等于maxDistance则认为是同一点
    public int getIndex(PointEx pointEx, int maxDistance) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).equals(pointEx, maxDistance)) {
                return i;
            }
        }
        return -1;
    }

    //取得某个点在列表中的全部索引
    //两点横纵坐标差值均小于等于maxDistance则认为是同一点
    public ArrayList<Integer> getIndexList(PointEx pointEx, int maxDistance) {
        ArrayList<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).equals(pointEx, maxDistance)) {
                indexList.add(i);
            }
        }
        return indexList;
    }

    public ArrayList<Integer> getIndexList(PointEx pointEx) {
        return this.getIndexList(pointEx, Constants.MAX_DISTANCE);
    }

    //去除当前列表中的重复点，若两点横纵坐标差值均小于等于maxDistance则随机删除一个
    //实际上是复制一份PointList，将当前的PointList清空，再将不重复的点加入进去
    public void dropDuplicates(int maxDistance) {
        PointList cloneList = (PointList) this.clone();
        this.clear();
        for (PointEx pointEx : cloneList) {
            if (!this.has(pointEx, maxDistance)) {
                this.add(pointEx);
            }
        }
    }

    //取得指定点的角度，返回值为角度制
    //作者 苏崇博
    public int getAngle(int index) {
        Point p1 = this.get(index - 1);
        Point p2 = this.get(index);
        Point p3 = this.get(index + 1);
        double delta_x1 = p1.x - p2.x;
        double delta_x2 = p3.x - p2.x;
        double delta_y1 = p1.y - p2.y;
        double delta_y2 = p3.y - p2.y;
        double arrayValue = delta_x1 * delta_x2 + delta_y1 * delta_y2;
        double len1 = Math.sqrt(delta_x1 * delta_x1 + delta_y1 * delta_y1);
        double len2 = Math.sqrt(delta_x2 * delta_x2 + delta_y2 * delta_y2);
        double cosValue = arrayValue / (len1 * len2);
        return (int) Math.toDegrees(Math.acos(cosValue));
    }

    //寻找列表中距离该点最近的一个点
    public PointEx getNearestPoint(PointEx pointEx) {
        PointEx retPoint = this.get(0);
        int minDistance = pointEx.getDistance(this.get(0));
        for (PointEx p : this) {
            int distance = pointEx.getDistance(p);
            if (distance < minDistance) {
                minDistance = distance;
                retPoint = p;
            }
        }
        return retPoint;
    }
}
