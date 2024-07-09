package com.bupt.inklue.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bupt.inklue.R;
import com.bupt.inklue.data.DatabaseManager;
import com.bupt.inklue.data.FileManager;

//设置页面
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean needUpdate = false;//是否需要更新练习记录

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_clear_cache).setOnClickListener(this);
        findViewById(R.id.button_reset_database).setOnClickListener(this);
        findViewById(R.id.button_download_resource).setOnClickListener(this);
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_clear_cache) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.clear_cache);
            builder.setMessage(R.string.clear_cache_warning);
            builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
                if (FileManager.clearDirectory(this.getExternalCacheDir() + "")) {
                    Toast.makeText(this, R.string.cache_cleared, Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (view.getId() == R.id.button_reset_database) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.reset_database);
            builder.setMessage(R.string.reset_database_warning);
            builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
                DatabaseManager.resetDatabase(this);
                needUpdate = true;//标记需要更新练习记录
            });
            builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (view.getId() == R.id.button_download_resource) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.download_resource);
            builder.setMessage(R.string.download_resource_warning);
            builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
                //启动初始化页面，并关闭当前页面
                Intent intent = new Intent();
                intent.setClass(this, InitActivity.class);
                startActivity(intent);
                finish();
            });
            builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    //关闭页面
    public void finish() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putBoolean("needUpdate", needUpdate);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_FIRST_USER, intent);
        super.finish();
    }
}
