package com.bupt.evaluate.extract.out_of_use;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.PointList;
import com.bupt.evaluate.data.out_of_use.PointTree;
import com.bupt.evaluate.data.out_of_use.TreeNode;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

//树状结构绘制器，实现节点操作器(被搁置的方法)
public class TreeDrawer implements NodeOperator {

    public Mat img;
    public PointList contour;

    public TreeDrawer(Mat img, PointList contour) {
        this.img = img;
        this.contour = contour;
    }

    //绘制树状结构，应属于图像绘制器ImageDrawer(被搁置的方法)
    public static void drawPointTree(Mat img, PointTree pointTree, Contours contours) {
        for (int i = 0; i < contours.size(); i++) {
            TreeDrawer treeDrawer = new TreeDrawer(img, contours.get(i));
            pointTree.dfs(pointTree.rootNode.children.get(i), 0, 0, treeDrawer);
        }
    }

    public void operate(TreeNode node, int depth, int breadth) {
        PointEx pointEx = contour.get(node.index.get(0));
        Imgproc.circle(img, pointEx, 3, new Scalar(255, 0, 255), -1);
        PointEx temp = new PointEx(pointEx);
        temp.x -= 60;
        Imgproc.putText(img, " (" + depth + "," + breadth + ")", temp,
                0, 0.5, new Scalar(255, 0, 255), 1);
    }
}
