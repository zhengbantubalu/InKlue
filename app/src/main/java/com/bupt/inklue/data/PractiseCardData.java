package com.bupt.inklue.data;

import android.graphics.Bitmap;

//用于存放练习卡片数据的类
public class PractiseCardData {
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