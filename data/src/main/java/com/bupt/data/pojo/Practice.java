package com.bupt.data.pojo;

import java.io.Serializable;
import java.util.ArrayList;

//练习
public class Practice implements Serializable {

    public ArrayList<HanZi> hanZiList;//汉字数据列表
    private long id;//本地数据库主键
    private String name = "";//名称
    private String coverPath = "";//封面图像路径
    private String time = "";//完成时刻
    private String hanZiCodes = "";//汉字编码字符串
    private String hanZiLogIds = "";//汉字记录ID字符串

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHanZiCodes() {
        return hanZiCodes;
    }

    public void setHanZiCodes(String hanZiCodes) {
        this.hanZiCodes = hanZiCodes;
    }

    public String getHanZiLogIds() {
        return hanZiLogIds;
    }

    public void setHanZiLogIds(String hanZiLogIds) {
        this.hanZiLogIds = hanZiLogIds;
    }
}
