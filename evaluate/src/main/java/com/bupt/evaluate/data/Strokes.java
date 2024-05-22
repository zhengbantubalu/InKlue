package com.bupt.evaluate.data;

import com.bupt.evaluate.extract.StrokeExtractor;
import com.bupt.evaluate.util.Constants;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

//一个汉字的笔画列表，每个元素是类型为Stroke的一个笔画，每个笔画的元素是PointEx类
public class Strokes extends ArrayList<Stroke> {

    //调用笔画提取器，根据轮廓和特征点提取汉字笔画
    public static Strokes extractStrokes(String className, Contours contours, Points points) {
        return StrokeExtractor.extractStrokes(className, contours, points);
    }

    //根据输入笔画和标准笔画取得笔画评价数据，储存在输入笔画的每一个笔画中
    public static void evaluateStrokes(Strokes inputStrokes, Strokes stdStrokes, Mat img) {
        Imgproc.cvtColor(img, img, Imgproc.COLOR_RGBA2RGB);//转换图像通道，避免绘制时颜色异常
        int strokeIndex = 0;
        for (int i = 0; i < stdStrokes.size(); i++) {
            inputStrokes.get(i).evaluation = Stroke.evaluateStroke(
                    inputStrokes.get(i), stdStrokes.get(i), img, strokeIndex);
            if (!inputStrokes.get(i).evaluation.advice.isEmpty()) {
                strokeIndex += 1;
            }
        }
    }

    //分别去除每个笔画中的重复点，若两点横纵坐标差值均小于等于maxDistance则随机删除一个
    public void dropDuplicates(int maxDistance) {
        for (Stroke stroke : this) {
            stroke.dropDuplicates(maxDistance);
        }
    }

    public void dropDuplicates() {
        this.dropDuplicates(Constants.MAX_DISTANCE);
    }
}
