package com.bupt.inklue.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bupt.inklue.R;
import com.bupt.inklue.adapter.CharCardAdapter;
import com.bupt.inklue.adapter.CharCardDecoration;
import com.bupt.inklue.data.PracticeData;
import com.bupt.inklue.data.PracticeDataManager;

//练习详情页面
public class PracticeActivity extends AppCompatActivity implements View.OnClickListener {

    private CharCardAdapter adapter;//卡片适配器
    private PracticeData practiceData;//练习数据

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        //取得练习数据
        practiceData = (PracticeData) getIntent().getSerializableExtra("practiceData");
        if (practiceData != null) {
            practiceData.charsData = PracticeDataManager.getStdCharsData(this, practiceData);
        }

        //设置练习标题
        TextView textView = findViewById(R.id.textview_title);
        textView.setText(practiceData.getName());

        //初始化RecyclerView
        initRecyclerView();

        //设置RecyclerView中项目的点击监听器
        adapter.setOnItemClickListener(this::startWritingActivity);

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_start).setOnClickListener(this);
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_start) {
            checkCameraPermission();
        }
    }

    //权限申请的回调，用于在获取权限后继续刚才中断的操作
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && grantResults.length != 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //权限申请成功，继续启动拍照页面
            startCameraActivity();
        }
    }

    //启动书写页面
    private void startWritingActivity(int position) {
        Intent intent = new Intent();
        intent.setClass(this, WritingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("practiceData", practiceData);
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //启动拍照页面
    private void startCameraActivity() {
        Intent intent = new Intent();
        intent.setClass(this, CameraActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("practiceData", practiceData);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //检查相机权限
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            //拥有权限，启动拍照页面
            startCameraActivity();
        } else {
            //无权限则申请
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 0);
        }
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_practice);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        CharCardDecoration decoration = new CharCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new CharCardAdapter(this, practiceData.charsData);
        recyclerView.setAdapter(adapter);//设置卡片适配器
    }
}
