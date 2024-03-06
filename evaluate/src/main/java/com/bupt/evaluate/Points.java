package com.bupt.evaluate;

import org.opencv.core.Mat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

//点集，每个元素是一种点的列表，每一个点是一个Point类
public class Points extends HashMap<Integer, PointList> {
    //点列表类型对应的索引
    public static int END = 0;
    public static int MID = 1;
    public static int INTER = 2;

    //从细化图像中取得点集
    public Points(Mat img) {
        this.put(0, new PointList());
        this.put(1, new PointList());
        this.put(2, new PointList());
        int rows = img.rows();
        int cols = img.cols();
        for (int i = 2; i < rows - 2; i++) {
            for (int j = 2; j < cols - 2; j++) {
                if ((int) img.get(i, j)[0] == 255) {
                    int branchNum = getBranchNum(img, i, j);
                    if (branchNum < 2) {
                        this.get(0).add(new Point(j, i));
                    } else if (branchNum > 2) {
                        this.get(2).add(new Point(j, i));
                    }
                }
            }
        }
        this.dropDuplicates(10);
    }

    //分别去除每个列表中的重复点，若两点横纵坐标差值均小于等于maxDistance则随机删除一个
    public void dropDuplicates(int maxDistance) {
        for (int key : this.keySet()) {
            PointList thisList = this.get(key);
            if (thisList != null && !thisList.isEmpty()) {
                thisList.dropDuplicates(maxDistance);
            }
        }
    }

    //搜索指定的列表是否已经含有某个点，传入-1则搜索全部点
    //两点横纵坐标差值均小于等于maxDistance则认为是同一点，要求两点坐标完全相同需传入0
    public boolean has(int key, Point point, int maxDistance) {
        Set<Integer> range;
        if (key == -1) {
            range = this.keySet();
        } else {
            range = new HashSet<>();
            range.add(key);
        }
        for (int k : range) {
            PointList thisList = this.get(k);
            if (thisList != null && !thisList.isEmpty()) {
                if (thisList.has(point, maxDistance)) {
                    return true;
                }
            }
        }
        return false;
    }

    //取得一个白色像素的分支数，即8邻域中白色像素的个数
    private int getBranchNum(Mat img, int i, int j) {
        int branchNum = -1;
        for (int x = i - 1; x <= i + 1; x++) {
            for (int y = j - 1; y <= j + 1; y++) {
                if ((int) img.get(x, y)[0] == 255) {
                    branchNum += 1;
                }
            }
        }
        if (branchNum != 2) {
            branchNum = getBranchNumPro(img, i, j);
        }
        return branchNum;
    }

    //取得端点或交点的准确分支数，即8邻域外一周白色像素出现的次数
    private int getBranchNumPro(Mat img, int i, int j) {
        int branchNumPro = 0;
        int[] coord = new int[]{i - 3, j - 2};
        boolean[] values = new boolean[]{false, false};
        for (int k = -1; k < 16; k++) {
            values = getValues(img, values, coord, k);
            branchNumPro += (values[0] != values[1]) ? 1 : 0;
        }
        return branchNumPro / 2;
    }

    //取得当前像素与前一像素的值
    private boolean[] getValues(Mat img, boolean[] values, int[] coord, int k) {
        int num = k / 4;
        if (num < 2) {
            coord[num % 2] += 1;
        } else {
            coord[num % 2] -= 1;
        }
        values[0] = values[1];
        values[1] = img.get(coord[0], coord[1])[0] == 255;
        return values;
    }
}
