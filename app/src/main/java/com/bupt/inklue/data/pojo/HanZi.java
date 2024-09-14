package com.bupt.inklue.data.pojo;

import java.io.Serializable;

//汉字
public class HanZi implements Serializable {

    private String code = "";//编码
    private String name = "";//名称
    private String url = "";//网址
    private String work = "";//碑帖
    private String artist = "";//作者
    private String era = "";//年代
    private String style = "";//书体
    private String path = "";//标准图像路径
    private String writtenPath = "";//书写图像路径
    private String feedbackPath = "";//反馈图像途径
    private String extractPath = "";//笔画提取结果图像路径
    private String score = "";//对书写的评分
    private String advice = "";//对书写的建议

    //判断此汉字数据是否符合筛选条件
    public boolean match(HanZi filter) {
        String name = filter.getName();
        String style = filter.getStyle();
        String era = filter.getEra();
        String artist = filter.getArtist();
        String work = filter.getWork();
        return (name.isEmpty() || name.equals(this.name)) &&
                (style.isEmpty() || style.equals(this.style)) &&
                (era.isEmpty() || era.equals(this.era)) &&
                (artist.isEmpty() || artist.equals(this.artist)) &&
                (work.isEmpty() || work.equals(this.work));
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getWrittenPath() {
        return writtenPath;
    }

    public void setWrittenPath(String writtenPath) {
        this.writtenPath = writtenPath;
    }

    public String getFeedbackPath() {
        return feedbackPath;
    }

    public void setFeedbackPath(String feedbackPath) {
        this.feedbackPath = feedbackPath;
    }

    public String getExtractPath() {
        return extractPath;
    }

    public void setExtractPath(String extractPath) {
        this.extractPath = extractPath;
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
