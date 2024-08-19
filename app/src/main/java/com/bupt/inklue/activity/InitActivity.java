package com.bupt.inklue.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bupt.inklue.R;
import com.bupt.inklue.data.DatabaseManager;
import com.bupt.inklue.data.FileManager;

//初始化页面
public class InitActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        //初始化目录
        FileManager.initDirectory(this);
        //异步进行网络操作
        Toast.makeText(this, R.string.downloading, Toast.LENGTH_SHORT).show();
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            //重置数据库
            DatabaseManager.resetDatabase(this);
            //下载资源图片
            FileManager.downloadImg(this, findViewById(R.id.progress_bar));
            //下载完成后执行
            handler.post(() -> {
                //创建练习封面
                FileManager.createPracticesCover(this);
                //显示反馈信息
                Toast.makeText(this, R.string.database_reset, Toast.LENGTH_SHORT).show();
                //标记App为非首次启动
                SharedPreferences preferences = getSharedPreferences("app", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isFirstLaunch", false);
                editor.apply();
                //启动主页面，并关闭当前页面
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                finish();
            });
        }).start();
    }
}
