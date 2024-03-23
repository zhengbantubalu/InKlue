package com.bupt.evaluate.processor.out_of_use;

import com.bupt.evaluate.data.TreeNode;

//节点操作器(被搁置的方法)
public interface NodeOperator {
    //对树节点的操作
    void operate(TreeNode node, int depth, int breadth);
}
