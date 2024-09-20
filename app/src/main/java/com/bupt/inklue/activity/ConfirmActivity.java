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

import com.bupt.data.pojo.Practice;
import com.bupt.inklue.R;
import com.bupt.inklue.adapter.CheckCardAdapter;
import com.bupt.inklue.decoration.HanZiCardDecoration;

//确认页面
public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener {

    public static ConfirmActivity confirmActivity;//用于在图像检查页面中结束此页面
    private CheckCardAdapter adapter;//卡片适配器
    private Practice practice;//练习数据

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        confirmActivity = this;

        //取得练习数据
        practice = (Practice) getIntent().getSerializableExtra(getString(R.string.practice_bundle));

        //设置练习标题
        TextView textView = findViewById(R.id.textview_title);
        textView.setText(practice.getName());

        //修改开始按钮文字
        Button button_start = findViewById(R.id.button_start);
        button_start.setText(R.string.confirm);

        //初始化RecyclerView
        initRecyclerView();

        //设置RecyclerView中项目的点击监听器
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
            startResultActivity();
        }
    }

    //子页面关闭的回调
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //更新练习数据
        practice = (Practice) intent.getSerializableExtra(getString(R.string.practice_bundle));
        if (practice != null) {
            adapter.update(practice.hanZiList);
        }
    }

    //启动图像检查页面
    private void startCheckActivity(int position) {
        Intent intent = new Intent();
        intent.setClass(this, CheckActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.practice_bundle), practice);
        bundle.putInt(getString(R.string.position_bundle), position);
        intent.putExtras(bundle);
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }

    //启动评价结果页面
    private void startResultActivity() {
        Intent intent = new Intent();
        intent.setClass(this, ResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.practice_bundle), practice);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_practice);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        HanZiCardDecoration decoration = new HanZiCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new CheckCardAdapter(this, practice.hanZiList);
        recyclerView.setAdapter(adapter);//设置卡片适配器
    }
}
