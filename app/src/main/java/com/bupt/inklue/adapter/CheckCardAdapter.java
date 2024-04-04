package com.bupt.inklue.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bupt.inklue.data.CardsData;

//检查卡片适配器
public class CheckCardAdapter extends CharCardAdapter {

    private final boolean[] isUpdated;//标记每个对象是否被更新过

    public CheckCardAdapter(Context context, CardsData imageCardsData) {
        super(context, imageCardsData);
        isUpdated = new boolean[imageCardsData.size()];
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isUpdated[position]) {
            //设置卡片图片
            Bitmap bitmap = BitmapFactory.decodeFile(imageCardsData.get(position).getWrittenImgPath());
            holder.imageView.setImageBitmap(bitmap);
            //设置卡片名称
            holder.textView.setText(imageCardsData.get(position).getName());
            //设置卡片的点击监听器
            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(holder.getAdapterPosition());
                }
            });
        } else {
            //设置卡片名称
            holder.textView.setText(imageCardsData.get(position).getName());
            //设置卡片的点击监听器
            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(holder.getAdapterPosition());
                }
            });
            isUpdated[position] = true;
        }
    }

    //更新全部数据
    @SuppressLint("NotifyDataSetChanged")//忽略更新具体数据的要求
    public void update(CardsData imageCardsData) {
        this.imageCardsData = imageCardsData;
        notifyDataSetChanged();
    }

    //更新指定数据
    public void update(CardsData imageCardsData, int position) {
        this.imageCardsData = imageCardsData;
        notifyItemChanged(position);
    }
}
