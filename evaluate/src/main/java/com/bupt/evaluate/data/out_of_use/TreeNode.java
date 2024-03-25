package com.bupt.evaluate.data.out_of_use;

import java.util.ArrayList;

//树节点，用于在轮廓中提取树状结构
public class TreeNode {
    public ArrayList<Integer> index;//该点在点列表PointList中的所有索引的列表
    public ArrayList<TreeNode> children;//子节点列表
    public int position;//该点在父节点的子节点列表中的索引
    public TreeNode parent;//父节点

    //用于创建根节点
    public TreeNode() {
        this.children = new ArrayList<>();
        this.parent = null;
    }

    //用于创建一般节点
    public TreeNode(int index, int position, TreeNode parent) {
        this.index = new ArrayList<>();
        this.index.add(index);
        this.children = new ArrayList<>();
        this.position = position;
        this.parent = parent;
    }
}
