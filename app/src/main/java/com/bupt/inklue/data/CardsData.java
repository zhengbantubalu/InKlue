package com.bupt.inklue.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//卡片数据列表
public class CardsData extends ArrayList<CardData> implements Serializable {

    //空构造方法，用于创建空列表
    public CardsData() {
    }

    //将List<CardData>转为本类
    public CardsData(List<CardData> cardsData) {
        this.addAll(cardsData);
    }
}
