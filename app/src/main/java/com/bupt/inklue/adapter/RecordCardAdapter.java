package com.bupt.inklue.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bupt.inklue.data.CardsData;

//记录卡片适配器
public class RecordCardAdapter extends CharCardAdapter {

    public RecordCardAdapter(Context context, CardsData imageCardsData) {
        super(context, imageCardsData);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        //设置卡片资源
        holder.textView.setText(imageCardsData.get(position).getScore());//设置卡片名称为评分
        Bitmap bitmap = BitmapFactory.decodeFile(imageCardsData.get(position).getWrittenImgPath());
        holder.imageView.setImageBitmap(bitmap);//设置卡片图片
        //设置卡片的点击监听器
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
    }
}
