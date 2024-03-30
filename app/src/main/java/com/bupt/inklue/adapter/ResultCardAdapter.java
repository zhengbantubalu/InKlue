package com.bupt.inklue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bupt.inklue.R;
import com.bupt.inklue.data.ResultCardData;

import java.util.List;

//搜索结果卡片适配器
public class ResultCardAdapter extends RecyclerView.Adapter<ResultCardAdapter.ViewHolder> {

    private final Context context;
    private final List<ResultCardData> result_cards_data;
    private OnItemClickListener listener;

    public ResultCardAdapter(Context context, List<ResultCardData> result_cards_data) {
        this.context = context;
        this.result_cards_data = result_cards_data;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public int getItemCount() {
        return result_cards_data.size();
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        //设置卡片资源
        holder.textView.setText(result_cards_data.get(position).getName());//设置卡片名称
        holder.imageView.setImageBitmap(result_cards_data.get(position).getImage());//设置卡片图片
        //设置卡片的点击监听器
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_result_card, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview_result_name);
            imageView = itemView.findViewById(R.id.imageview_result_image);
        }
    }
}
