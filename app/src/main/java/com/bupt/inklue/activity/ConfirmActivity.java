package com.bupt.inklue.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bupt.inklue.R;
import com.bupt.inklue.adapter.ConfirmCardAdapter;
import com.bupt.inklue.adapter.ImageCardDecoration;
import com.bupt.inklue.data.CardData;
import com.bupt.inklue.data.CardsData;

import java.util.ArrayList;

//确认页面
public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener {

    private ConfirmCardAdapter adapter;//图像检查卡片适配器
    private CardsData imageCardsData = new CardsData();//图像卡片数据

    @SuppressWarnings("unchecked")//忽略取得图像卡片数据时类型转换产生的警告
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        //取得Intent数据
        imageCardsData = new CardsData((ArrayList<CardData>)
                (getIntent().getSerializableExtra("imageCardsData")));
        String practiceName = getIntent().getStringExtra("practiceName");

        //设置练习标题
        TextView textView = findViewById(R.id.title);
        textView.setText(practiceName);

        //修改开始按钮文字
        Button button_start = findViewById(R.id.button_start);
        button_start.setText(R.string.confirm);

        //初始化RecyclerView
        initRecyclerView();

        //RecyclerView中项目的点击监听器
        adapter.setOnItemClickListener(this::startCheckActivity);

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_start).setOnClickListener(this);
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_start) {
        }
    }

    //子页面关闭的回调
    @SuppressWarnings("unchecked")//忽略取得图像卡片数据时类型转换产生的警告
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //更新图像卡片数据
        imageCardsData = new CardsData((ArrayList<CardData>)
                (intent.getSerializableExtra("imageCardsData")));
        adapter.update(imageCardsData);
    }

    //启动图片检查页面
    private void startCheckActivity(int position) {
        Intent intent = new Intent();
        intent.setClass(this, CheckActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("imageCardsData", imageCardsData);
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_practice_detail);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        ImageCardDecoration decoration = new ImageCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new ConfirmCardAdapter(this, imageCardsData);
        recyclerView.setAdapter(adapter);//设置图像卡片适配器
    }
}
