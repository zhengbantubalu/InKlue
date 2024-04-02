package com.bupt.inklue.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

//“练习”碎片
public class PracticeFragment extends Fragment {

    private View root;//根视图
    private Context context;//环境
    private ListView listView;//用于排列练习卡片的类
    private final CardsData practiceCardsData = new CardsData();//练习卡片数据

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_practice, container, false);
            context = getContext();

            //初始化ListView
            initListView();

            //ListView中项目的点击监听器
            listView.setOnItemClickListener((adapterView, view, i, l) -> {
                Intent intent = new Intent();
                intent.setClass(context, PracticeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("practiceCardData", practiceCardsData.get(i - 1));
                intent.putExtras(bundle);
                startActivity(intent);
            });

            //“添加”按钮的点击监听器
            ImageButton button_add = root.findViewById(R.id.button_add);
            button_add.setOnClickListener(view -> {
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
        View emptyView = new View(context);
        listView.addHeaderView(emptyView);//将ListView的头视图设为空
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
                    "/" + c.get(i - 1) + ".jpg");
            practiceCardsData.add(cardData);
        }
    }
}
