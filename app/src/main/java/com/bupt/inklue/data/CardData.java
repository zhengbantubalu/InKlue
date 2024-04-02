package com.bupt.inklue.data;

import java.io.Serializable;

//卡片数据
public class CardData implements Serializable {
    private String name;//名称
    private String stdImgPath;//标准图像路径
    private String subtitle;//副标题
    private String time;//时间
    private String writtenImgPath;//书写图像路径

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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWrittenImgPath() {
        return writtenImgPath;
    }

    public void setWrittenImgPath(String writtenImgPath) {
        this.writtenImgPath = writtenImgPath;
    }
}
