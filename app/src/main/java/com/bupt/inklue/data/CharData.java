package com.bupt.inklue.data;

import java.io.Serializable;

//汉字数据
public class CharData implements Serializable {

    private long id;//主键
    private String name;//名称
    private String stdImgPath;//标准图像路径
    private String writtenImgPath;//书写图像路径
    private String score;//对书写的评分
    private String advice;//对书写的建议

    public long getID() {
        return id;
    }

    public void setID(long id) {
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
