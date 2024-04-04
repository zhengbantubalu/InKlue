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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bupt.inklue.R;
import com.bupt.inklue.activity.PracticeActivity;
import com.bupt.inklue.activity.TestActivity;
import com.bupt.inklue.adapter.PracticeCardAdapter;
import com.bupt.inklue.data.CardData;
import com.bupt.inklue.data.CardsData;

import java.util.ArrayList;
import java.util.Arrays;

//“我的”碎片
public class UserFragment extends Fragment {

    private View root;//根视图
    private Context context;//环境
    private ListView listView;//用于排列练习卡片的类
    private View userCard;//用户卡片视图
    private final CardsData practiceCardsData = new CardsData();//练习卡片数据

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_user, container, false);
            userCard = inflater.inflate(R.layout.item_user_card, container, false);
            context = getContext();

            //初始化ListView
            initListView();

            //ListView中项目的点击监听器
            listView.setOnItemClickListener((adapterView, view, i, l) -> {
                Intent intent = new Intent();
                intent.setClass(context, PracticeActivity.class);
                startActivity(intent);
            });

            //“设置”按钮的点击监听器
            ImageButton button_settings = root.findViewById(R.id.button_settings);
            button_settings.setOnClickListener(view -> {
                Intent intent = new Intent();
                intent.setClass(context, TestActivity.class);
                startActivity(intent);
            });
        }
        return root;
    }

    //初始化ListView
    private void initListView() {
        listView = root.findViewById(R.id.listview_practice);
        setCardsData();//设置练习卡片数据
        setUserCard();//设置用户卡片
        listView.addHeaderView(userCard);//将ListView的头视图设为用户卡片
        View emptyView = new View(context);
        listView.addFooterView(emptyView);//将ListView的尾视图设为空
        PracticeCardAdapter adapter = new PracticeCardAdapter(context, practiceCardsData);
        listView.setAdapter(adapter);//设置练习卡片适配器
    }

    //设置练习卡片数据
    private void setCardsData() {
        ArrayList<String> c = new ArrayList<>(Arrays.asList(
                "土", "王", "五", "上", "下", "不", "之", "山", "廿", "四", "日", "石", "六", "天"));
        for (int i = 1; i <= c.size(); i++) {
            CardData cardData = new CardData();
            cardData.setName(c.get(i - 1));
            cardData.setStdImgPath(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                    "/" + c.get(i - 1) + "1.jpg");
            practiceCardsData.add(cardData);
        }
    }

    //设置用户卡片
    private void setUserCard() {
        ImageView user_avatar = userCard.findViewById(R.id.user_avatar);
        Bitmap bitmap = BitmapFactory.decodeFile(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                        "/avatar.jpg");
        user_avatar.setImageBitmap(bitmap);
    }
}
