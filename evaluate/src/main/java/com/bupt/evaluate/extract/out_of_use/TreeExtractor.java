package com.bupt.evaluate.extract.out_of_use;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.out_of_use.PointTree;
import com.bupt.evaluate.data.out_of_use.TreeNode;
import com.bupt.evaluate.util.Constants;

import java.util.ArrayList;

//树状结构提取器，用于提取一个汉字的树状结构(被搁置的方法)
public class TreeExtractor {

    //从轮廓中提取树状结构
    public static PointTree contours2Tree(Contours contours) {
        PointTree pointTree = new PointTree();
        //遍历轮廓列表
        for (int i = 0; i < contours.size(); i++) {
            PointList contour = contours.get(i);
            PointList pointStack = new PointList();
            pointStack.add(contour.get(0));
            TreeNode subRootNode = new TreeNode(0, i, pointTree.rootNode);
            pointTree.rootNode.children.add(subRootNode);
            ArrayList<TreeNode> nodeStack = new ArrayList<>();
            nodeStack.add(subRootNode);
            TreeNode currentNode = subRootNode;
            //遍历一条轮廓的所有点
            for (int j = 1; j < contour.size(); j++) {
                PointEx point = contour.get(j);
                int angle = contour.getAngle(j);
                //该点是拐点
                if (angle < Constants.MIN_ANGLE || angle > 360 - Constants.MIN_ANGLE) {
                    //获取该点在栈中的索引
                    int index = pointStack.getIndex(point);
                    if (index == -1) {
                        //该点不在栈中，则添加该点到栈中与树中
                        pointStack.add(point);
                        TreeNode childNode = new TreeNode(j, currentNode.children.size(), currentNode);
                        nodeStack.add(childNode);
                        currentNode.children.add(childNode);
                        currentNode = childNode;
                    } else {
                        //该点在栈中，则将栈回退到该点之前出现时的状态，并将索引加入节点的列表
                        pointStack.subList(index + 1, pointStack.size()).clear();
                        nodeStack.subList(index + 1, nodeStack.size()).clear();
                        currentNode = nodeStack.get(index);
                        currentNode.index.add(j);
                    }
                }
            }
        }
        return pointTree;
    }
}
