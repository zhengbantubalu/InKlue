package com.bupt.inklue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bupt.inklue.R;
import com.bupt.inklue.data.PractiseCardData;

import java.util.List;

//练习卡片适配器
public class PractiseCardAdapter extends BaseAdapter {

    private final List<PractiseCardData> practise_cards_data;
    private final Context context;

    public PractiseCardAdapter(List<PractiseCardData> practise_cards_data, Context context) {
        this.practise_cards_data = practise_cards_data;
        this.context = context;
    }

    public int getCount() {
        return practise_cards_data.size();
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.card_practise, viewGroup, false);
        }
        //设置卡片资源
        TextView name = view.findViewById(R.id.textview_practise_name);
        name.setText(practise_cards_data.get(position).getName());
        ImageView image = view.findViewById(R.id.imageview_practise_image);
        image.setImageBitmap(practise_cards_data.get(position).getImage());
        return view;
    }
}