package com.bupt.evaluate.evaluate;

import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.util.Constants;
import com.bupt.evaluate.util.ImageDrawer;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;

//对一个笔画书写的评价
public class StrokeEvaluation {
    public int score;//评分
    public String advice;//建议
    public Mat outputMat;//输出图片

    public StrokeEvaluation() {
        this.score = 0;
        this.advice = "";
        this.outputMat = new Mat();
    }

    //输入笔画为空，反馈错误信息
    public static StrokeEvaluation emptyError(Stroke stroke, Mat img, int strokeIndex) {
        StrokeEvaluation strokeEvaluation = new StrokeEvaluation();
        strokeEvaluation.score = 0;
        strokeEvaluation.advice = "第" + (strokeIndex + 1) + "笔未识成功，请规范书写";
        ImageDrawer.drawStroke(img, stroke, new Scalar(Constants.COLOR_YELLOW),
                new Scalar(Constants.COLOR_RED), strokeIndex);
        return strokeEvaluation;
    }
}
