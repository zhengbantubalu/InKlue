package com.bupt.inklue.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//用于设置汉字卡片间距的装饰类
public class HanZiCardDecoration extends RecyclerView.ItemDecoration {

    private final int spacing;

    public HanZiCardDecoration(int spacing) {
        this.spacing = spacing;
    }

    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);

        if (position < 3) {
            outRect.top = spacing;
        }
        outRect.right = spacing;
        outRect.bottom = spacing;
    }
}
