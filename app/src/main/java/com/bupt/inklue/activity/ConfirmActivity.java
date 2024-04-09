package com.bupt.inklue.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bupt.inklue.R;
import com.bupt.inklue.adapter.CharCardDecoration;
import com.bupt.inklue.adapter.CheckCardAdapter;
import com.bupt.inklue.data.CharData;
import com.bupt.inklue.data.PracticeData;
import com.bupt.inklue.util.BitmapProcessor;

//确认页面
public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener {

    private CheckCardAdapter adapter;//卡片适配器
    private PracticeData practiceData;//练习数据
    private boolean isFinished = false;//预处理是否完成

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        //取得练习数据
        practiceData = (PracticeData) getIntent().getSerializableExtra("practiceData");

        //设置练习标题
        TextView textView = findViewById(R.id.textview_title);
        textView.setText(practiceData.getName());

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

        //异步预处理拍摄的图片
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            for (int i = 0; i < practiceData.charsData.size(); i++) {
                CharData charData = practiceData.charsData.get(i);
                BitmapProcessor.preprocess(charData.getWrittenImgPath(), 512);
                int position = i;
                handler.post(() -> adapter.update(practiceData.charsData, position));
            }
            isFinished = true;
        }).start();
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_start) {
            if (isFinished) {
                startResultActivity();
            } else {
                Toast.makeText(this, R.string.evaluating, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //子页面关闭的回调
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //更新练习数据
        practiceData = (PracticeData) intent.getSerializableExtra("practiceData");
        if (practiceData != null) {
            adapter.update(practiceData.charsData);
        }
    }

    //启动图片检查页面
    private void startCheckActivity(int position) {
        Intent intent = new Intent();
        intent.setClass(this, CheckActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("practiceData", practiceData);
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }

    //启动评价结果页面
    private void startResultActivity() {
        Intent intent = new Intent();
        intent.setClass(this, ResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("practiceData", practiceData);
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
        CharCardDecoration decoration = new CharCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new CheckCardAdapter(this, practiceData.charsData);
        recyclerView.setAdapter(adapter);
    }
}
