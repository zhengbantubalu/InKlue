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

    private final Context context;
    private final List<PractiseCardData> practise_cards_data;

    public PractiseCardAdapter(Context context, List<PractiseCardData> practise_cards_data) {
        this.context = context;
        this.practise_cards_data = practise_cards_data;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_practise_card,
                    viewGroup, false);
        }

        //设置卡片资源
        TextView name = view.findViewById(R.id.textview_practise_name);
        name.setText(practise_cards_data.get(position).getName());//设置卡片名称
        ImageView image = view.findViewById(R.id.imageview_practise_image);
        image.setImageBitmap(practise_cards_data.get(position).getImage());//设置卡片图片

        return view;
    }
}
