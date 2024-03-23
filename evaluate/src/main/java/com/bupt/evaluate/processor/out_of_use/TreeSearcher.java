package com.bupt.evaluate.processor.out_of_use;

import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.TreeNode;

import java.util.ArrayList;

//树状结构查找器，实现节点操作器(被搁置的方法)
public class TreeSearcher implements NodeOperator {

    public PointList contour;//要查找的轮廓
    public PointEx start;//要查找的起始点
    public PointEx end;//要查找的终止点
    public ArrayList<Integer> startIndexes;//起始点在PointList中的全部索引
    public ArrayList<Integer> endIndexes;//终止点在PointList中的全部索引

    public TreeSearcher(PointList contour, PointEx start, PointEx end) {
        this.contour = contour;
        this.start = start;
        this.end = end;
        this.startIndexes = new ArrayList<>();
        this.endIndexes = new ArrayList<>();
    }

    //如果找到匹配的点则保存
    public void operate(TreeNode node, int depth, int breadth) {
        if (this.start.equals(contour.get(node.index.get(0)))) {
            this.startIndexes.addAll(node.index);
        } else if (this.end.equals(contour.get(node.index.get(0)))) {
            this.endIndexes.addAll(node.index);
        }
    }
}
