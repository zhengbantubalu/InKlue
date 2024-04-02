package com.bupt.inklue.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bupt.inklue.R;
import com.bupt.inklue.activity.ImageActivity;
import com.bupt.inklue.adapter.ImageCardAdapter;
import com.bupt.inklue.adapter.ImageCardDecoration;
import com.bupt.inklue.data.CardData;
import com.bupt.inklue.data.CardsData;

import java.util.ArrayList;
import java.util.Arrays;

//“搜索”碎片
public class SearchFragment extends Fragment {

    private View root;//根视图
    private Context context;//环境
    private ImageCardAdapter adapter;//图像卡片适配器
    private final CardsData resultCardsData = new CardsData();//搜索结果卡片数据

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_search, container, false);
            context = getContext();

            //初始化RecyclerView
            initRecyclerView();

            //RecyclerView中项目的点击监听器
            adapter.setOnItemClickListener(position -> {
                Intent intent = new Intent();
                intent.setClass(context, ImageActivity.class);
                //在请求中附带图片数据
                Log.d("appTest", resultCardsData.toString());
                Bundle bundle = new Bundle();
                bundle.putSerializable("imageCardsData", resultCardsData);
                bundle.putInt("position", position);
                intent.putExtras(bundle);
                startActivity(intent);
            });

            //“搜索”按钮的点击监听器
            ImageButton button_submit = root.findViewById(R.id.button_search_submit);
            button_submit.setOnClickListener(view -> {

            });
        }
        return root;
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_search);
        setCardsData();//设置图像卡片数据
        GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        ImageCardDecoration decoration = new ImageCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new ImageCardAdapter(context, resultCardsData);
        recyclerView.setAdapter(adapter);//设置图像卡片适配器
    }

    //设置图像卡片数据
    private void setCardsData() {
        ArrayList<String> c = new ArrayList<>(Arrays.asList(
                "土", "王", "五", "上", "下", "不", "之", "山", "廿", "四", "日", "石", "六", "天",
                "土", "王", "五", "上", "下", "不", "之", "山", "廿", "四", "日", "石", "六", "天",
                "土", "王", "五", "上", "下", "不", "之", "山", "廿", "四", "日", "石", "六", "天"));
        for (int i = 1; i <= c.size(); i++) {
            CardData cardData = new CardData();
            cardData.setName(c.get(i - 1));
            String name;
            if (i <= c.size() / 3) {
                name = "/" + c.get(i - 1) + ".jpg";
            } else if (i <= c.size() / 3 * 2) {
                name = "/" + c.get(i - 1) + "1.jpg";
            } else {
                name = "/" + c.get(i - 1) + "2.jpg";
            }
            String pathName = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + name;
            cardData.setStdImgPath(pathName);
            resultCardsData.add(cardData);
        }
    }
}
