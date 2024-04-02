package com.bupt.inklue.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bupt.inklue.R;
import com.bupt.inklue.data.CardsData;

//练习卡片适配器
public class PracticeCardAdapter extends BaseAdapter {

    private final Context context;
    private final CardsData practiceCardsData;

    public PracticeCardAdapter(Context context, CardsData practiceCardsData) {
        this.context = context;
        this.practiceCardsData = practiceCardsData;
    }

    public int getCount() {
        return practiceCardsData.size();
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
        name.setText(practiceCardsData.get(position).getName());//设置卡片名称
        ImageView image = view.findViewById(R.id.imageview_practise_image);
        Bitmap bitmap = BitmapFactory.decodeFile(practiceCardsData.get(position).getStdImgPath());
        image.setImageBitmap(bitmap);//设置卡片图片

        return view;
    }
}
