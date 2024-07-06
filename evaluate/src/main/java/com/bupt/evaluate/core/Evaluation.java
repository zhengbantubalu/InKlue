package com.bupt.evaluate.core;

import android.graphics.Bitmap;

//对一个汉字书写的评价
public class Evaluation {
    public int score;//评分
    public String advice;//建议
    public Bitmap outputBmp;//输出图片

    public Evaluation() {
        this.score = 0;
        this.advice = "";
    }
}
