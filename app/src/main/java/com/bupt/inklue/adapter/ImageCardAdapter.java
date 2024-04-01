package com.bupt.inklue.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bupt.inklue.R;
import com.bupt.inklue.data.CardData;

import java.util.List;

//图像卡片适配器
public class ImageCardAdapter extends RecyclerView.Adapter<ImageCardAdapter.ViewHolder> {

    private final Context context;
    private final List<CardData> image_cards_data;
    private OnItemClickListener listener;

    public ImageCardAdapter(Context context, List<CardData> image_cards_data) {
        this.context = context;
        this.image_cards_data = image_cards_data;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public int getItemCount() {
        return image_cards_data.size();
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        //设置卡片资源
        holder.textView.setText(image_cards_data.get(position).getName());//设置卡片名称
        Bitmap bitmap = BitmapFactory.decodeFile(image_cards_data.get(position).getImgPath());
        holder.imageView.setImageBitmap(bitmap);//设置卡片图片
        //设置卡片的点击监听器
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_image_card, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview_card_name);
            imageView = itemView.findViewById(R.id.imageview_card_image);
        }
    }
}
