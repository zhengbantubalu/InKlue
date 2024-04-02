package com.bupt.inklue.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bupt.inklue.data.CardsData;

//确认页面图像卡片适配器
public class ConfirmCardAdapter extends ImageCardAdapter {

    public ConfirmCardAdapter(Context context, CardsData imageCardsData) {
        super(context, imageCardsData);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        //设置卡片资源
        holder.textView.setText(imageCardsData.get(position).getName());//设置卡片名称
        Bitmap bitmap = BitmapFactory.decodeFile(imageCardsData.get(position).getWrittenImgPath());
        holder.imageView.setImageBitmap(bitmap);//设置卡片图片
        //设置卡片的点击监听器
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    //更新数据
    @SuppressLint("NotifyDataSetChanged")//忽略更新具体数据的要求
    public void update(CardsData imageCardsData) {
        this.imageCardsData = imageCardsData;
        notifyDataSetChanged();
    }
}
