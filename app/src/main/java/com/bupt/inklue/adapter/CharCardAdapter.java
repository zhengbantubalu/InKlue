package com.bupt.inklue.adapter;

import android.annotation.SuppressLint;
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
import com.bupt.inklue.data.CharData;

import java.util.ArrayList;

//汉字卡片适配器
public class CharCardAdapter extends RecyclerView.Adapter<CharCardAdapter.ViewHolder> {

    private final Context context;
    protected ArrayList<CharData> charsData;
    protected OnItemClickListener listener;

    public CharCardAdapter(Context context, ArrayList<CharData> charsData) {
        this.context = context;
        this.charsData = charsData;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public int getItemCount() {
        return charsData.size();
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        //设置卡片资源
        holder.textView.setText(charsData.get(position).getName());//设置卡片名称
        Bitmap bitmap = BitmapFactory.decodeFile(charsData.get(position).getStdImgPath());
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
                R.layout.item_char_card, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview_card_name);
            imageView = itemView.findViewById(R.id.imageview_card_image);
        }
    }

    //更新全部数据
    @SuppressLint("NotifyDataSetChanged")//忽略更新具体数据的要求
    public void update(ArrayList<CharData> charsData) {
        this.charsData = charsData;
        notifyDataSetChanged();
    }

    //更新指定数据
    public void update(ArrayList<CharData> charsData, int position) {
        this.charsData = charsData;
        notifyItemChanged(position);
    }
}
