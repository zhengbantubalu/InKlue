package com.bupt.evaluate.evaluate;

import org.opencv.core.Mat;

import java.util.ArrayList;

//笔画评价构建器
public class EvaluationBuilder {
    public ArrayList<Integer> scores;//评分
    public ArrayList<String> advices;//建议
    public Mat outputMat;//输出图片
    public boolean isDrawn;//已经绘制了标准笔画

    public EvaluationBuilder() {
        this.scores = new ArrayList<>();
        this.advices = new ArrayList<>();
        this.outputMat = new Mat();
        this.isDrawn = false;
    }

    //将本类转换为对笔画的评价
    public StrokeEvaluation toStrokeEvaluation(int strokeIndex) {
        StrokeEvaluation strokeEvaluation = new StrokeEvaluation();
        strokeEvaluation.score = getAverageScore();
        strokeEvaluation.advice = getStrokeAdvice(strokeIndex);
        strokeEvaluation.outputMat = this.outputMat;
        return strokeEvaluation;
    }

    //取得平均分
    public int getAverageScore() {
        int sum = 0;
        for (Integer score : scores) {
            sum += score;
        }
        return sum / scores.size();
    }

    //取得对笔画的建议
    public String getStrokeAdvice(int strokeIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(outputScores());//加入具体分数，用于测试
        if (!advices.isEmpty()) {
            stringBuilder.append("第").append(strokeIndex + 1).append("笔请");
            for (String advice : advices) {
                stringBuilder.append(advice).append("；");
            }
            stringBuilder.setCharAt(stringBuilder.length() - 1, '\n');
        }
        return stringBuilder.toString();
    }

    //取得具体分数字符串，用于测试
    public String outputScores() {
        StringBuilder stringBuilder = new StringBuilder();
        if (scores.size() == 1) {
            stringBuilder.append("// 相似度:").append(scores.get(0)).append("\n");
        } else {
            stringBuilder.append("// 平直度:").append(scores.get(0));
            stringBuilder.append(" 长度:").append(scores.get(1));
            stringBuilder.append(" 倾角:").append(scores.get(2));
            stringBuilder.append(" 位置:").append(scores.get(3));
            stringBuilder.append(" 平均:").append(getAverageScore()).append("\n");
        }
        return stringBuilder.toString();
    }
}
