package com.bupt.evaluate;

import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//点列表，每个元素是一个PointEx类
public class PointList extends ArrayList<PointEx> {

    public PointList() {
    }

    //将OpenCV的List<Point>转为本类
    public PointList(List<? extends Point> points) {
        for (Point point : points) {
            this.add(new PointEx(point));
        }
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

    //重写ArrayList的get方法，使其可以接受负数和超出范围的索引
    public PointEx get(int index) {
        if (index >= 0) {
            return super.get(index % this.size());
        } else {
            return super.get(index + this.size());
        }
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

    //取得某个点在列表中的全部索引
    //两点横纵坐标差值均小于等于maxDistance则认为是同一点
    public ArrayList<Integer> getIndex(PointEx pointEx, int maxDistance) {
        ArrayList<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).equals(pointEx, maxDistance)) {
                indexList.add(i);
            }
        }
        return indexList;
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

    //取得指定点的角度
    //作者 苏崇博
    public int getAngle(int index) {
        double delta_x1 = this.get(index - 1).x - this.get(index).x;
        double delta_x2 = this.get(index + 1).x - this.get(index).x;
        double delta_y1 = this.get(index - 1).y - this.get(index).y;
        double delta_y2 = this.get(index + 1).y - this.get(index).y;
        double arrayValue = delta_x1 * delta_x2 + delta_y1 * delta_y2;
        double len1 = Math.sqrt(delta_x1 * delta_x1 + delta_y1 * delta_y1);
        double len2 = Math.sqrt(delta_x2 * delta_x2 + delta_y2 * delta_y2);
        double cosValue = arrayValue / (len1 * len2);
        return (int) Math.toDegrees(Math.acos(cosValue));
    }
}
