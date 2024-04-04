package com.bupt.inklue.data;

import java.io.Serializable;

//卡片数据
public class CardData implements Serializable {
    private int id;//主键
    private String name;//名称
    private String stdImgPath;//标准图像路径
    private String writtenImgPath;//书写图像路径
    private String advice;//对书写的建议
    private int score;//对书写的评分

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStdImgPath() {
        return stdImgPath;
    }

    public void setStdImgPath(String pathName) {
        this.stdImgPath = pathName;
    }

    public String getWrittenImgPath() {
        return writtenImgPath;
    }

    public void setWrittenImgPath(String writtenImgPath) {
        this.writtenImgPath = writtenImgPath;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getScore() {
        return Integer.toString(score);
//        if (score == 100) {
//            return "ACE";
//        } else if (score >= 90) {
//            return "SSS";
//        } else if (score >= 80) {
//            return "SS";
//        } else if (score >= 70) {
//            return "S";
//        } else if (score >= 60) {
//            return "A";
//        } else if (score >= 50) {
//            return "B";
//        } else if (score >= 40) {
//            return "C";
//        } else if (score >= 30) {
//            return "D";
//        } else {
//            return "E";
//        }
    }

    public void setScore(int score) {
        this.score = score;
    }
}
