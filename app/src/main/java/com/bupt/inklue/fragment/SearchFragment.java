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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bupt.inklue.R;
import com.bupt.inklue.activity.TestActivity;
import com.bupt.inklue.adapter.ResultCardAdapter;
import com.bupt.inklue.adapter.ResultCardDecoration;
import com.bupt.inklue.data.ResultCardData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//“搜索”页面
public class SearchFragment extends Fragment {

    private View root;//根视图
    private Context context;//环境
    private final List<ResultCardData> result_cards_data = new ArrayList<>();//搜索结果卡片数据

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_search, container, false);
        }
        context = getContext();

        //设置RecyclerView
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_search);
        setCardsData();//设置卡片数据
        GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        ResultCardDecoration decoration = new ResultCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        ResultCardAdapter adapter = new ResultCardAdapter(context, result_cards_data);
        recyclerView.setAdapter(adapter);//调用搜索结果卡片适配器
        //RecyclerView中项目的点击监听器
        adapter.setOnItemClickListener(new ResultCardAdapter.OnItemClickListener() {
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.setClass(context, TestActivity.class);
                startActivity(intent);
            }
        });

        //“搜索”按钮的点击监听器
        ImageButton button_submit = root.findViewById(R.id.button_submit);
        button_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        return root;
    }

    //设置卡片数据
    private void setCardsData() {
        ArrayList<String> c = new ArrayList<>(Arrays.asList(
                "土", "王", "五", "上", "下", "不", "之", "山", "廿", "四", "日", "石", "六", "天",
                "土", "王", "五", "上", "下", "不", "之", "山", "廿", "四", "日", "石", "六", "天",
                "土", "王", "五", "上", "下", "不", "之", "山", "廿", "四", "日", "石", "六", "天"));
        for (int i = 1; i <= c.size(); i++) {
            ResultCardData bean = new ResultCardData();
            bean.setName(c.get(i - 1));
            String name;
            if (i <= c.size() / 3) {
                name = "/" + c.get(i - 1) + ".jpg";
            } else if (i <= c.size() / 3 * 2) {
                name = "/" + c.get(i - 1) + "1.jpg";
            } else {
                name = "/" + c.get(i - 1) + "2.jpg";
            }
            Bitmap bitmap = BitmapFactory.decodeFile(
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + name);
            bean.setImage(bitmap);
            result_cards_data.add(bean);
        }
    }
}
