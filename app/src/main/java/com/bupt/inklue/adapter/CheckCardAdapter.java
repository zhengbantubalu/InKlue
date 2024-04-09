package com.bupt.inklue.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bupt.inklue.data.CharData;

import java.util.ArrayList;

//检查卡片适配器
public class CheckCardAdapter extends CharCardAdapter {

    private final boolean[] isUpdated;//标记每个对象是否被更新过

    public CheckCardAdapter(Context context, ArrayList<CharData> charsData) {
        super(context, charsData);
        isUpdated = new boolean[charsData.size()];
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isUpdated[position]) {
            //设置卡片图片
            Bitmap bitmap = BitmapFactory.decodeFile(charsData.get(position).getWrittenImgPath());
            holder.imageView.setImageBitmap(bitmap);
            //设置卡片名称
            holder.textView.setText(charsData.get(position).getName());
            //设置卡片的点击监听器
            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(holder.getAdapterPosition());
                }
            });
        } else {
            //设置卡片名称
            holder.textView.setText(charsData.get(position).getName());
            //设置卡片的点击监听器
            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(holder.getAdapterPosition());
                }
            });
            isUpdated[position] = true;
        }
    }
}
