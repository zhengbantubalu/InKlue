package com.bupt.inklue.data;

import java.io.Serializable;

//卡片数据
public class CardData implements Serializable {
    private String name;
    private String imgPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String pathName) {
        this.imgPath = pathName;
    }
}
