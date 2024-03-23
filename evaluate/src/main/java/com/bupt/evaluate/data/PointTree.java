package com.bupt.evaluate.data;

import com.bupt.evaluate.processor.out_of_use.NodeOperator;

//轮廓关键点组成的树状结构
public class PointTree {
    public TreeNode rootNode;//根节点

    public PointTree() {
        this.rootNode = new TreeNode();
    }

    //深度优先遍历指定节点下的所有节点
    public void dfs(TreeNode node, int depth, int breadth, NodeOperator operator) {
        if (node == null) {
            return;
        }
        operator.operate(node, depth, breadth);
        for (int i = 0; i < node.children.size(); i++) {
            dfs(node.children.get(i), depth + 1, i, operator);
        }
    }
}
