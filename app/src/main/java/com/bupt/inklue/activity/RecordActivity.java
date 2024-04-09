package com.bupt.inklue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bupt.inklue.R;
import com.bupt.inklue.adapter.CharCardDecoration;
import com.bupt.inklue.adapter.ResultCardAdapter;
import com.bupt.inklue.data.PracticeData;
import com.bupt.inklue.data.PracticeDataManager;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener {

    private ResultCardAdapter adapter;//卡片适配器
    private PracticeData recordData;//记录数据

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        //隐藏底部栏
        findViewById(R.id.bottom_bar).setVisibility(View.GONE);

        //取得记录数据
        long id = getIntent().getLongExtra("recordID", 0);
        recordData = PracticeDataManager.getRecordData(this, id);

        //设置练习标题
        TextView textView = findViewById(R.id.textview_title);
        textView.setText(recordData.getName());

        //初始化RecyclerView
        initRecyclerView();

        //RecyclerView中项目的点击监听器
        adapter.setOnItemClickListener(this::startEvaluateActivity);

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        }
    }

    //启动评价页面
    private void startEvaluateActivity(int position) {
        Intent intent = new Intent();
        intent.setClass(this, EvaluateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("practiceData", recordData);
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_practice);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        CharCardDecoration decoration = new CharCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new ResultCardAdapter(this, recordData.charsData);
        recyclerView.setAdapter(adapter);//设置卡片适配器
    }
}
