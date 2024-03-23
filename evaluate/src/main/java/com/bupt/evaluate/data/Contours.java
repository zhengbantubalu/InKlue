package com.bupt.evaluate.data;

import java.util.ArrayList;
import java.util.Collections;

//一个汉字的轮廓列表
public class Contours extends ArrayList<PointList> {

    //寻找轮廓中距离该点最近的一个点
    public PointEx findNearestPoint(PointEx pointEx) {
        PointEx retPoint = new PointEx();
        int minDistance = pointEx.getDistance(this.get(0).get(0));
        for (PointList contour : this) {
            for (PointEx p : contour) {
                int distance = pointEx.getDistance(p);
                if (distance < minDistance) {
                    minDistance = distance;
                    retPoint = p;
                }
            }
        }
        return retPoint;
    }

    //根据起止点查找匹配轮廓，返回匹配的轮廓段，并正序排列，无匹配则返回空
    public PointList findMatchContour(PointEx start, PointEx end) {
        for (int i = 0; i < this.size(); i++) {
            PointList contour = this.get(i);
            ArrayList<Integer> startIndexes = contour.getIndexList(start);
            ArrayList<Integer> endIndexes = contour.getIndexList(end);
            if (!startIndexes.isEmpty() && !endIndexes.isEmpty()) {
                return getMatchContour(contour, startIndexes, endIndexes);
            }
        }
        return null;
    }

    //根据起止点位置获取两个点之间的最短轮廓连线
    public PointList getMatchContour(PointList contour, ArrayList<Integer> startIndexes, ArrayList<Integer> endIndexes) {
        int[] indexArray = getNearestIndexes(startIndexes, endIndexes, contour.size());
        int fromIndex = indexArray[0];
        int toIndex = indexArray[1];
        PointList pointList;
        if (Math.abs(fromIndex - toIndex) < contour.size() / 2) {
            if (fromIndex < toIndex) {
                pointList = new PointList(contour.subList(fromIndex, toIndex + 1));
            } else {
                PointList tempList = new PointList(contour.subList(toIndex, fromIndex + 1));
                Collections.reverse(tempList);
                pointList = tempList;
            }
        } else {
            if (fromIndex < toIndex) {
                pointList = new PointList();
                PointList tempList = new PointList(contour.subList(0, fromIndex + 1));
                Collections.reverse(tempList);
                pointList.addAll(tempList);
                tempList = new PointList(contour.subList(toIndex, contour.size()));
                Collections.reverse(tempList);
                pointList.addAll(tempList);
            } else {
                pointList = new PointList(contour.subList(fromIndex, contour.size()));
                PointList tempList = new PointList(contour.subList(0, toIndex + 1));
                pointList.addAll(tempList);
            }
        }
        return pointList;
    }

    //取得两个索引列表中距离最近的两个索引
    private int[] getNearestIndexes(ArrayList<Integer> startIndexes, ArrayList<Integer> endIndexes, int size) {
        int[] indexArray = new int[2];
        int fromIndex = startIndexes.get(0);
        int toIndex = getNearestIndexes(fromIndex, endIndexes, size);
        int minDistance = Math.abs(fromIndex - toIndex);
        indexArray[0] = fromIndex;
        indexArray[1] = toIndex;
        for (int i = 1; i < startIndexes.size(); i++) {
            fromIndex = startIndexes.get(i);
            toIndex = getNearestIndexes(fromIndex, endIndexes, size);
            int distance = Math.abs(fromIndex - toIndex);
            distance = Math.min(distance, size - distance);
            if (distance < minDistance) {
                minDistance = distance;
                indexArray[0] = fromIndex;
                indexArray[1] = toIndex;
            }
        }
        return indexArray;
    }

    //取得索引列表indexes在PointList中距离fromIndex最近的索引
    private int getNearestIndexes(int fromIndex, ArrayList<Integer> indexes, int size) {
        int toIndex = indexes.get(0);
        int minDistance = Math.abs(fromIndex - toIndex);
        for (int i = 1; i < indexes.size(); i++) {
            int distance = Math.abs(fromIndex - indexes.get(i));
            distance = Math.min(distance, size - distance);
            if (distance < minDistance) {
                minDistance = distance;
                toIndex = indexes.get(i);
            }
        }
        return toIndex;
    }
}
