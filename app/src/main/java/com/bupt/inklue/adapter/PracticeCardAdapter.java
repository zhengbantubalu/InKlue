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

import com.bupt.data.pojo.Practice;
import com.bupt.inklue.R;

import java.util.ArrayList;

//练习卡片适配器
public class PracticeCardAdapter extends RecyclerView.Adapter<PracticeCardAdapter.ViewHolder> {

    private final Context context;
    protected ArrayList<Practice> practiceList;
    protected OnItemClickListener clickListener;
    protected OnItemLongClickListener longClickListener;

    public PracticeCardAdapter(Context context, ArrayList<Practice> practiceList) {
        this.context = context;
        this.practiceList = practiceList;
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public int getItemCount() {
        return practiceList.size();
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        //设置卡片资源
        holder.textView.setText(practiceList.get(position).getName());//设置卡片名称
        Bitmap bitmap = BitmapFactory.decodeFile(practiceList.get(position).getCoverPath());
        holder.imageView.setImageBitmap(bitmap);//设置卡片图片
        //设置卡片的点击监听器
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(holder.getAdapterPosition());
            }
        });
        //设置卡片的长按监听器
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(holder.getAdapterPosition());
            }
            return true;
        });
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_practice_card, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview_practice_image);
            textView = itemView.findViewById(R.id.textview_practice_name);
        }
    }

    //更新全部数据
    @SuppressLint("NotifyDataSetChanged")//忽略更新具体数据的要求
    public void update(ArrayList<Practice> practicesData) {
        this.practiceList = practicesData;
        notifyDataSetChanged();
    }
}
