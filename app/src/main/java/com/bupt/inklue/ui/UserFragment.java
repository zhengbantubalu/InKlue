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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.bupt.inklue.R;
import com.bupt.inklue.adapters.PractiseCardAdapter;
import com.bupt.inklue.data.PractiseCardData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//“我的”页面
public class UserFragment extends Fragment {
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
            bean.setName(c.get(i - 1));
            Bitmap bitmap = BitmapFactory.decodeFile(
                    getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                            "/" + c.get(i - 1) + "1.jpg");
            bean.setImage(bitmap);
            practise_cards_data.add(bean);
        }
        //取得ListView
        ListView listView = root.findViewById(R.id.listview_practise);
        //将ListView的头视图设为用户卡片，尾视图设为ViewStub
        View viewStub = inflater.inflate(R.layout.viewstub, null);
        listView.addFooterView(viewStub);
        View userCard = inflater.inflate(R.layout.card_user, null);
        //设置用户头像
        ImageView user_avatar = userCard.findViewById(R.id.user_avatar);
        Bitmap bitmap = BitmapFactory.decodeFile(
                getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                        "/avatar.jpg");
        user_avatar.setImageBitmap(bitmap);
        listView.addHeaderView(userCard);
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
