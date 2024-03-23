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
    public PointEx get(int index) {
        if (index >= 0) {
            return super.get(index % this.size());
        } else {
            return super.get(index + this.size());
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

    public void dropDuplicates() {
        this.dropDuplicates(Constants.MAX_DISTANCE);
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

    public boolean has(PointEx pointEx) {
        return this.has(pointEx, Constants.MAX_DISTANCE);
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

    public int getIndex(PointEx pointEx) {
        return this.getIndex(pointEx, Constants.MAX_DISTANCE);
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

    //判断当前列表是否与输入的起止点匹配
    //0为不匹配，1为正序匹配，2为倒序匹配
    //两点横纵坐标差值均小于等于maxDistance则认为是同一点
    public int match(PointEx start, PointEx end, int maxDistance) {
        PointEx pointStart = this.get(0);
        PointEx pointEnd = this.get(this.size() - 1);
        if (pointStart.equals(start, maxDistance) && pointEnd.equals(end, maxDistance)) {
            return 1;
        } else if (pointStart.equals(end, maxDistance) && pointEnd.equals(start, maxDistance)) {
            return 2;
        } else {
            return 0;
        }
    }

    public int match(PointEx start, PointEx end) {
        return this.match(start, end, Constants.MAX_DISTANCE);
    }

    //取得指定点的角度
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
}
