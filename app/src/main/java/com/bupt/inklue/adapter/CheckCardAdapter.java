package com.bupt.inklue.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bupt.data.pojo.HanZi;

import java.util.ArrayList;

//检查卡片适配器
public class CheckCardAdapter extends HanZiCardAdapter {

    public CheckCardAdapter(Context context, ArrayList<HanZi> hanZiList) {
        super(context, hanZiList);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        //设置卡片资源
        holder.textView.setText(hanZiList.get(position).getName());//设置卡片名称
        Bitmap bitmap = BitmapFactory.decodeFile(hanZiList.get(position).getExtractPath());
        holder.imageView.setImageBitmap(bitmap);//设置卡片图片
        //设置卡片的点击监听器
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
    }
}
