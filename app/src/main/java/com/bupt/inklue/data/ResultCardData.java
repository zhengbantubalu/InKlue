package com.bupt.inklue.data;

import android.graphics.Bitmap;

//用于存放搜索结果卡片数据的类
public class ResultCardData {
    private String name;
    private Bitmap image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
