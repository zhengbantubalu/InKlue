package com.bupt.inklue.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bupt.inklue.R;
import com.bupt.inklue.data.api.HanZiApi;
import com.bupt.inklue.data.api.PracticeApi;
import com.bupt.inklue.data.db.DatabaseManager;
import com.bupt.inklue.util.DirectoryHelper;

//初始化页面
public class InitActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        //初始化目录
        DirectoryHelper.initDir(this);
        //初始化数据库
        DatabaseManager.initDatabase(this);
        //异步进行网络操作
        Toast.makeText(this, R.string.downloading, Toast.LENGTH_SHORT).show();
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            //初始化数据
            HanZiApi.initHanZiData(this);
            PracticeApi.initPracticeData(this);
            //初始化资源
            HanZiApi.initHanZiResource(this, findViewById(R.id.progress_bar));
            PracticeApi.initPracticeResource(this);
            //网络操作完成后执行
            handler.post(() -> {
                Toast.makeText(this, R.string.download_finished, Toast.LENGTH_SHORT).show();
                //标记App为非首次启动
                SharedPreferences preferences = getSharedPreferences(
                        getString(R.string.preference_name), MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(getString(R.string.is_first_launch), false);
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
