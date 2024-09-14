package com.bupt.inklue.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bupt.inklue.R;
import com.bupt.inklue.adapter.PracticeCardAdapter;
import com.bupt.inklue.data.api.PracticeApi;
import com.bupt.inklue.data.pojo.HanZi;
import com.bupt.inklue.data.pojo.Practice;
import com.bupt.inklue.decoration.PracticeCardDecoration;

import java.util.ArrayList;

//选择页面，用于选择汉字要添加到的练习
public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    private PracticeCardAdapter adapter;//卡片适配器
    private ArrayList<Practice> practiceList;//练习数据列表
    private HanZi hanZi;//选中的汉字
    private boolean needUpdate = false;//是否需要更新练习列表

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        //取得汉字数据
        hanZi = (HanZi) getIntent().getSerializableExtra(getString(R.string.han_zi_bundle));

        //取得练习数据列表
        practiceList = PracticeApi.getPracticeList(this);

        //初始化RecyclerView
        initRecyclerView();

        //设置RecyclerView中项目的点击监听器
        adapter.setOnItemClickListener(position -> {
            //向练习中添加汉字
            PracticeApi.addHanZiIntoPractice(this, practiceList.get(position), hanZi);
            needUpdate = true;
            finish();
        });

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        }
    }

    //关闭页面
    public void finish() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putBoolean(getString(R.string.update_bundle), needUpdate);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_FIRST_USER, intent);
        super.finish();
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_practice);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        PracticeCardDecoration decoration = new PracticeCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new PracticeCardAdapter(this, practiceList);
        recyclerView.setAdapter(adapter);//设置卡片适配器
    }
}
