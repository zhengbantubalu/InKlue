package com.bupt.evaluate.core;

import android.graphics.Bitmap;

import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

//对一个汉字书写的评价
public class Evaluation {

    public int score;//评分
    public String advice;//建议
    public Bitmap outputBmp;//输出图片

    public Evaluation() {
        this.score = 0;
        this.advice = "";
    }

    //根据每个笔画的评价设置汉字书写评价数据
    void setEvaluation(Strokes strokes, String cnChar, Bitmap inputBmp, Bitmap stdBmp) {
        int scoreSum = 0;
        int scoreNum = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (Stroke stroke : strokes) {
            if (stroke.evaluation.advice != null) {
                scoreSum += stroke.evaluation.score;
                stringBuilder.append(stroke.evaluation.advice);
                scoreNum += 1;
            }
        }
        if (scoreNum != 0) {
            this.score = scoreSum / scoreNum;
//            this.score = (int) (Math.sqrt((double) scoreSum / scoreNum) * 10);
        } else {
            this.score = 100;
        }
        if (this.score >= 90) {
            stringBuilder.append("这个“" + cnChar + "”字写得真好");
        } else if (this.score >= 70) {
            stringBuilder.append("这个“" + cnChar + "”字写得不错");
        } else {
            stringBuilder.append("这个“" + cnChar + "”字需要加油");
        }
        this.advice = stringBuilder.toString();
        Mat output = new Mat();
        if (!strokes.get(0).evaluation.outputMat.empty()) {
            output = strokes.get(0).evaluation.outputMat;
        } else {
            Utils.bitmapToMat(inputBmp, output, true);
        }
        Bitmap outputBmp = Bitmap.createBitmap(output.cols(), output.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(output, outputBmp);
        this.outputBmp = outputBmp;
    }
}
