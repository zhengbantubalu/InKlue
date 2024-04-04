package com.bupt.inklue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bupt.inklue.R;
import com.bupt.inklue.adapter.CharCardAdapter;
import com.bupt.inklue.adapter.CharCardDecoration;
import com.bupt.inklue.data.CardData;
import com.bupt.inklue.data.CardsData;

import java.util.ArrayList;
import java.util.Arrays;

//作业详情页面
public class PracticeActivity extends AppCompatActivity implements View.OnClickListener {

    private CardData practiceCardData;//练习数据
    private CharCardAdapter adapter;//汉字卡片适配器
    private final CardsData imageCardsData = new CardsData();//汉字卡片数据
    private String practiceName;//练习名称

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        //取得练习数据
        practiceCardData = (CardData)
                getIntent().getSerializableExtra("practiceCardData");

        //设置练习标题
        TextView textView = findViewById(R.id.title);
        if (practiceCardData != null) {
            practiceName = practiceCardData.getName();
        }
        textView.setText(practiceName);

        //取得汉字卡片数据
        getCardsData();

        //初始化RecyclerView
        initRecyclerView();

        //RecyclerView中项目的点击监听器
        adapter.setOnItemClickListener(this::startImageActivity);

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_start).setOnClickListener(this);
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_start) {
            startWritingActivity();
        }
    }

    //启动图片查看页面
    private void startImageActivity(int position) {
        Intent intent = new Intent();
        intent.setClass(this, ImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("imageCardsData", imageCardsData);
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //启动书写页面
    private void startWritingActivity() {
        Intent intent = new Intent();
        intent.setClass(this, WritingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("imageCardsData", imageCardsData);
        bundle.putString("practiceName", practiceName);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_practice_detail);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        CharCardDecoration decoration = new CharCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new CharCardAdapter(this, imageCardsData);
        recyclerView.setAdapter(adapter);//设置汉字卡片适配器
    }

    //取得汉字卡片数据
    private void getCardsData() {
        ArrayList<String> c = new ArrayList<>(Arrays.asList(
                "土", "王", "五", "上", "下", "不", "之", "山", "廿", "四", "日", "石", "六", "天"));
        for (int i = 1; i <= c.size(); i++) {
            CardData cardData = new CardData();
            cardData.setName(c.get(i - 1));
            cardData.setStdImgPath(getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                    "/" + c.get(i - 1) + ".jpg");
            imageCardsData.add(cardData);
        }
    }
}
