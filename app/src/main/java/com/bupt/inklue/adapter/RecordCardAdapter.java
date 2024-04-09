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
import com.bupt.inklue.data.PracticeData;

import java.util.ArrayList;

//记录卡片适配器
public class RecordCardAdapter extends RecyclerView.Adapter<RecordCardAdapter.ViewHolder> {

    private final Context context;
    protected ArrayList<PracticeData> practicesData;
    protected OnItemClickListener listener;

    public RecordCardAdapter(Context context, ArrayList<PracticeData> practicesData) {
        this.context = context;
        this.practicesData = practicesData;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public int getItemCount() {
        return practicesData.size();
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        //设置卡片资源
        holder.textViewName.setText(practicesData.get(position).getName());//设置卡片名称
        holder.textViewTime.setText(practicesData.get(position).getTime());//设置卡片时间
        Bitmap bitmap = BitmapFactory.decodeFile(practicesData.get(position).getCoverImgPath());
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
                R.layout.item_record_card, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewTime;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textview_practice_name);
            textViewTime = itemView.findViewById(R.id.textview_practice_time);
            imageView = itemView.findViewById(R.id.imageview_practice_image);
        }
    }

    //更新全部数据
    @SuppressLint("NotifyDataSetChanged")//忽略更新具体数据的要求
    public void update(ArrayList<PracticeData> practicesData) {
        this.practicesData = practicesData;
        notifyDataSetChanged();
    }
}
