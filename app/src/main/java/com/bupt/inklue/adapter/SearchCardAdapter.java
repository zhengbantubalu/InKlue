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

import com.bupt.data.pojo.HanZi;
import com.bupt.inklue.R;

import java.util.ArrayList;

//搜索结果卡片适配器
public class SearchCardAdapter extends RecyclerView.Adapter<SearchCardAdapter.ViewHolder> {

    private final Context context;
    protected ArrayList<HanZi> hanZiList;
    protected OnItemClickListener listener;

    public SearchCardAdapter(Context context, ArrayList<HanZi> hanZiList) {
        this.context = context;
        this.hanZiList = hanZiList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public int getItemCount() {
        return hanZiList.size();
    }

    public void onBindViewHolder(SearchCardAdapter.ViewHolder holder, int position) {
        //设置卡片资源
        holder.textViewCenter.setText(hanZiList.get(position).getWork());
        holder.textViewLeftTop.setText(hanZiList.get(position).getName());
        holder.textViewLeftBottom.setText(hanZiList.get(position).getStyle());
        holder.textViewRightTop.setText(hanZiList.get(position).getEra());
        holder.textViewRightBottom.setText(hanZiList.get(position).getArtist());
        Bitmap bitmap = BitmapFactory.decodeFile(hanZiList.get(position).getPath());
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
                R.layout.item_search_card, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCenter;
        TextView textViewLeftTop;
        TextView textViewLeftBottom;
        TextView textViewRightTop;
        TextView textViewRightBottom;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewCenter = itemView.findViewById(R.id.textview_center);
            textViewLeftTop = itemView.findViewById(R.id.textview_left_top);
            textViewLeftBottom = itemView.findViewById(R.id.textview_left_bottom);
            textViewRightTop = itemView.findViewById(R.id.textview_right_top);
            textViewRightBottom = itemView.findViewById(R.id.textview_right_bottom);
            imageView = itemView.findViewById(R.id.imageview_card_image);
        }
    }

    //更新全部数据
    @SuppressLint("NotifyDataSetChanged")//忽略更新具体数据的要求
    public void update(ArrayList<HanZi> hanZiList) {
        this.hanZiList = hanZiList;
        notifyDataSetChanged();
    }

    //更新指定数据
    public void update(ArrayList<HanZi> hanZiList, int position) {
        this.hanZiList = hanZiList;
        notifyItemChanged(position);
    }
}
