package com.bupt.evaluate.evaluate;

import org.opencv.core.Mat;

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
}
