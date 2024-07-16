package com.bupt.inklue.data;

//筛选条件
public class FilterCondition {

    private String name;//名称
    private String style;//书体
    private String era;//年代
    private String author;//作者
    private String copybook;//碑帖

    public FilterCondition() {
        this.name = "";
        this.style = "";
        this.era = "";
        this.author = "";
        this.copybook = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCopybook() {
        return copybook;
    }

    public void setCopybook(String copybook) {
        this.copybook = copybook;
    }
}
