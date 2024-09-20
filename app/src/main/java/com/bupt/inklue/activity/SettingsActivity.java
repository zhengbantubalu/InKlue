package com.bupt.inklue.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bupt.data.api.HanZiApi;
import com.bupt.data.api.PracticeApi;
import com.bupt.data.db.DatabaseManager;
import com.bupt.inklue.R;
import com.bupt.inklue.util.DirectoryHelper;

//设置页面
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean needUpdate = false;//是否需要更新练习记录

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_clear_cache).setOnClickListener(this);
        findViewById(R.id.button_reset_data).setOnClickListener(this);
        findViewById(R.id.button_download_resource).setOnClickListener(this);
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_clear_cache) {
            clearCache();
        } else if (view.getId() == R.id.button_reset_data) {
            resetData();
        } else if (view.getId() == R.id.button_download_resource) {
            downloadResource();
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

    //清空缓存
    private void clearCache() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.clear_cache);
        builder.setMessage(R.string.clear_cache_warning);
        builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
            if (DirectoryHelper.clearDir(this.getExternalCacheDir() + "")) {
                Toast.makeText(this, R.string.cache_cleared, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //重置数据
    private void resetData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.reset_database);
        builder.setMessage(R.string.reset_database_warning);
        builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
            //清空目录
            DirectoryHelper.clearDir(DirectoryHelper.getPracticeLogHanZiDir(this));
            DirectoryHelper.clearDir(DirectoryHelper.getPracticeLogCoverDir(this));
            DirectoryHelper.clearDir(DirectoryHelper.getPracticeCoverDir(this));
            //删除数据库
            DatabaseManager.deleteDatabase(this);
            //初始化数据库
            DatabaseManager.initDatabase(this);
            //异步进行网络操作
            Handler handler = new Handler(Looper.getMainLooper());
            new Thread(() -> {
                //初始化数据
                HanZiApi.initHanZiData(this);
                PracticeApi.initPracticeData(this);
                //初始化练习资源
                PracticeApi.initPracticeResource(this);
                //网络操作完成后执行
                handler.post(() -> {
                    Toast.makeText(this, R.string.data_reset, Toast.LENGTH_SHORT).show();
                    needUpdate = true;
                });
            }).start();
        });
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //下载资源
    private void downloadResource() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.download_resource);
        builder.setMessage(R.string.download_resource_warning);
        builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
            //清空目录
            DirectoryHelper.clearDir(DirectoryHelper.getPracticeLogHanZiDir(this));
            DirectoryHelper.clearDir(DirectoryHelper.getPracticeLogCoverDir(this));
            DirectoryHelper.clearDir(DirectoryHelper.getPracticeCoverDir(this));
            //启动初始化页面
            Intent intent = new Intent();
            intent.setClass(this, InitActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
