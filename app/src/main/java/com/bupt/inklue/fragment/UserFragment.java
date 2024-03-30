package com.bupt.inklue.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bupt.inklue.R;
import com.bupt.inklue.activity.TestActivity;
import com.bupt.inklue.adapter.PractiseCardAdapter;
import com.bupt.inklue.data.PractiseCardData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//“我的”页面
public class UserFragment extends Fragment {

    private View root;//根视图
    private Context context;//环境
    private final List<PractiseCardData> practise_cards_data = new ArrayList<>();//练习卡片数据

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_user, container, false);
        }
        context = getContext();

        //设置ListView
        ListView listView = root.findViewById(R.id.listview_practise);
        setCardsData();//设置卡片数据
        View userCard = getUserCard(inflater);//取得用户卡片
        listView.addHeaderView(userCard);//将ListView的头视图设为用户卡片
        View emptyView = new View(context);
        listView.addFooterView(emptyView);//将ListView的尾视图设为空
        PractiseCardAdapter adapter = new PractiseCardAdapter(context, practise_cards_data);
        listView.setAdapter(adapter);//调用练习卡片适配器

        //ListView中项目的点击监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(context, TestActivity.class);
                startActivity(intent);
            }
        });

        //“设置”按钮的点击监听器
        ImageButton button_settings = root.findViewById(R.id.button_settings);
        button_settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        return root;
    }

    //设置卡片数据
    private void setCardsData() {
        ArrayList<String> c = new ArrayList<>(Arrays.asList(
                "土", "王", "五", "上", "下", "不", "之", "山", "廿", "四", "日", "石", "六", "天"));
        for (int i = 1; i <= c.size(); i++) {
            PractiseCardData bean = new PractiseCardData();
            bean.setName(c.get(i - 1));
            Bitmap bitmap = BitmapFactory.decodeFile(
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                            "/" + c.get(i - 1) + "1.jpg");
            bean.setImage(bitmap);
            practise_cards_data.add(bean);
        }
    }

    //取得用户卡片
    private View getUserCard(LayoutInflater inflater) {
        View userCard = inflater.inflate(R.layout.item_user_card, null);
        ImageView user_avatar = userCard.findViewById(R.id.user_avatar);
        Bitmap bitmap = BitmapFactory.decodeFile(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                        "/avatar.jpg");
        user_avatar.setImageBitmap(bitmap);
        return userCard;
    }
}
