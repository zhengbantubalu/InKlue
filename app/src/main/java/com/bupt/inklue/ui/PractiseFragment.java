package com.bupt.inklue.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.bupt.inklue.R;
import com.bupt.inklue.adapters.PractiseCardAdapter;
import com.bupt.inklue.data.PractiseCardData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//“练习”页面
public class PractiseFragment extends Fragment {

    private View root;
    private List<PractiseCardData> practise_cards_data = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_practise, container, false);
        }
        //为每个卡片设置数据
        ArrayList<String> c = new ArrayList<>(Arrays.asList(
                "土", "王", "五", "上", "下", "不", "之", "山", "廿", "四", "日", "石", "六", "天"));
        for (int i = 1; i <= c.size(); i++) {
            PractiseCardData bean = new PractiseCardData();
            bean.setName("练习" + i);
            Bitmap bitmap = BitmapFactory.decodeFile(
                    getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                            "/" + c.get(i - 1) + ".jpg");
            bean.setImage(bitmap);
            practise_cards_data.add(bean);
        }

        ListView listView = root.findViewById(R.id.listview_practise);
        //以下三行用于实现ListView头尾的分割线
        View viewStub = inflater.inflate(R.layout.fragment_practise, null, false);
        //将ListView的头尾视图设为ViewStub空视图，即仅保留头尾的分割线
        listView.addHeaderView(viewStub);
        listView.addFooterView(viewStub);
        //调用练习卡片适配器
        listView.setAdapter(new PractiseCardAdapter(practise_cards_data, getActivity()));
        //ListView中项目的点击监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), TestActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}