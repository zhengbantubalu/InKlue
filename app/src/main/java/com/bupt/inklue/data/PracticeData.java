package com.bupt.inklue.data;

import java.io.Serializable;
import java.util.ArrayList;

//练习数据
public class PracticeData implements Serializable {

    public ArrayList<CharData> charsData;//汉字数据列表
    private long id;//主键
    private String name;//名称
    private String time;//完成时刻
    private String coverImgPath;//封面图像路径
    private String charIDs;//汉字ID字符串

    public PracticeData() {
        this.charsData = new ArrayList<>();
        this.name = "";
        this.time = "";
        this.coverImgPath = "";
        this.charIDs = "";
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCoverImgPath() {
        return coverImgPath;
    }

    public void setCoverImgPath(String pathName) {
        this.coverImgPath = pathName;
    }

    public String getCharIDs() {
        return charIDs;
    }

    public void setCharIDs(String charIDs) {
        this.charIDs = charIDs;
    }
}
