package com.bupt.inklue.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bupt.inklue.R;
import com.bupt.inklue.adapter.ImageCardAdapter;
import com.bupt.inklue.adapter.ImageCardDecoration;
import com.bupt.inklue.data.CardData;
import com.bupt.inklue.data.CardsData;

import java.util.ArrayList;
import java.util.Arrays;

//作业详情页面
public class PracticeActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;//环境
    private ImageCardAdapter adapter;//图像卡片适配器
    private CardsData imageCardsData;//图像卡片数据

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        context = this;

        //设置练习标题
        CardData practiceCardData = (CardData)
                getIntent().getSerializableExtra("practiceCardData");
        TextView textView = findViewById(R.id.title);
        if (practiceCardData != null) {
            textView.setText(practiceCardData.getName());
        }

        //初始化RecyclerView
        initRecyclerView();

        //RecyclerView中项目的点击监听器
        adapter.setOnItemClickListener(position -> {
            Intent intent = new Intent();
            intent.setClass(context, ImageActivity.class);
            //在请求中附带图片数据
            Bundle bundle = new Bundle();
            bundle.putSerializable("imageCardsData", imageCardsData);
            bundle.putInt("position", position);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_next_step).setOnClickListener(this);
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_next_step) {
            //检查摄像头调用权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED) {
                //拥有权限，启动拍照页面
                Intent intent = new Intent();
                intent.setClass(context, CameraActivity.class);
                startActivity(intent);
            } else {
                //无权限则申请
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, 0);
            }
        }
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = this.findViewById(R.id.recyclerview_practice_detail);
        imageCardsData = new CardsData();
        setCardsData();//设置图像卡片数据
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        ImageCardDecoration decoration = new ImageCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new ImageCardAdapter(this, imageCardsData);
        recyclerView.setAdapter(adapter);//设置图像卡片适配器
    }

    //设置图像卡片数据
    private void setCardsData() {
        ArrayList<String> c = new ArrayList<>(Arrays.asList(
                "土", "王", "五", "上", "下", "不", "之", "山", "廿", "四", "日", "石", "六", "天"));
        for (int i = 1; i <= c.size(); i++) {
            CardData cardData = new CardData();
            cardData.setName(c.get(i - 1));
            cardData.setImgPath(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                    "/" + c.get(i - 1) + "1.jpg");
            imageCardsData.add(cardData);
        }
    }

    //权限申请的回调，用于在获取权限后继续刚才中断的操作
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //权限申请成功，继续启动拍照页面
            Intent intent = new Intent();
            intent.setClass(context, CameraActivity.class);
            startActivity(intent);
        }
    }
}
