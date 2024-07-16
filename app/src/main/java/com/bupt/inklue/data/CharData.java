package com.bupt.inklue.data;

import java.io.Serializable;

//汉字数据
public class CharData implements Serializable {

    private long id;//主键
    private String name;//名称
    private String className;//提取器类名
    private String style;//书体
    private String era;//年代
    private String author;//作者
    private String copybook;//碑帖
    private String stdImgPath;//标准图像路径
    private String writtenImgPath;//书写图像路径
    private String extractImgPath;//笔画提取结果图像路径
    private String score;//对书写的评分
    private String advice;//对书写的建议

    public CharData() {
        this.name = "";
        this.className = "";
        this.style = "";
        this.era = "";
        this.author = "";
        this.copybook = "";
        this.stdImgPath = "";
        this.writtenImgPath = "";
        this.extractImgPath = "";
        this.score = "";
        this.advice = "";
    }

    //判断此汉字数据是否符合筛选条件
    public boolean match(FilterCondition filterCondition) {
        String name = filterCondition.getName();
        String style = filterCondition.getStyle();
        String era = filterCondition.getEra();
        String author = filterCondition.getAuthor();
        String copybook = filterCondition.getCopybook();
        return (name.isEmpty() ||
                name.equals(this.name) ||
                name.equals(this.style) ||
                name.equals(this.era) ||
                name.equals(this.author) ||
                name.equals(this.copybook)) &&
                (style.isEmpty() || style.equals(this.style)) &&
                (era.isEmpty() || era.equals(this.era)) &&
                (author.isEmpty() || author.equals(this.author)) &&
                (copybook.isEmpty() || copybook.equals(this.copybook));
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public String getExtractImgPath() {
        return extractImgPath;
    }

    public void setExtractImgPath(String extractImgPath) {
        this.extractImgPath = extractImgPath;
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
