package com.bupt.evaluate.extract;

import com.bupt.evaluate.data.PointEx;
import com.bupt.evaluate.data.Points;

import org.opencv.core.Mat;

import java.util.Objects;

//特征点提取器，用于提取一个汉字的特征点列表
public class PointExtractor {

    //从细化图像中提取笔画的端点和交点
    public static Points mat2Points(Mat img) {
        Points points = new Points();
        int rows = img.rows();
        int cols = img.cols();
        for (int i = 2; i < rows - 2; i++) {
            for (int j = 2; j < cols - 2; j++) {
                if ((int) img.get(i, j)[0] == 255) {
                    int branchNum = getBranchNum(img, i, j);
                    if (branchNum < 2) {
                        Objects.requireNonNull(points.get(Points.END)).add(new PointEx(j, i));
                    } else if (branchNum > 2) {
                        Objects.requireNonNull(points.get(Points.INTER)).add(new PointEx(j, i));
                    }
                }
            }
        }
        //去除重复点
        points.dropDuplicates();
        return points;
    }

    //取得一个白色像素的分支数，即8邻域中白色像素的个数
    private static int getBranchNum(Mat img, int i, int j) {
        int branchNum = -1;
        for (int x = i - 1; x <= i + 1; x++) {
            for (int y = j - 1; y <= j + 1; y++) {
                if ((int) img.get(x, y)[0] == 255) {
                    branchNum += 1;
                }
            }
        }
        if (branchNum != 2) {
            branchNum = getBranchNumPlus(img, i, j);
        }
        return branchNum;
    }

    //取得端点或交点的准确分支数，即8邻域外一周白色像素出现的次数
    private static int getBranchNumPlus(Mat img, int i, int j) {
        int branchNumPro = 0;
        int[] coord = new int[]{i - 3, j - 2};
        boolean[] values = new boolean[]{false, false};
        for (int k = -1; k < 16; k++) {
            getValues(img, values, coord, k);
            branchNumPro += (values[0] != values[1]) ? 1 : 0;
        }
        return branchNumPro / 2;
    }

    //取得当前像素与前一像素的值
    private static void getValues(Mat img, boolean[] values, int[] coord, int k) {
        int num = k / 4;
        if (num < 2) {
            coord[num % 2] += 1;
        } else {
            coord[num % 2] -= 1;
        }
        values[0] = values[1];
        values[1] = img.get(coord[0], coord[1])[0] == 255;
    }
}
