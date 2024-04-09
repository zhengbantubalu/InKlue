package com.bupt.inklue.activity;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_clear_cache).setOnClickListener(this);
        findViewById(R.id.button_reset_database).setOnClickListener(this);
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_clear_cache) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("清空缓存");
            builder.setMessage("所有未保存的图片将被清空");
            builder.setPositiveButton("确认", (dialog, which) -> {
                if (FileManager.clearDir(this.getExternalCacheDir() + "")) {
                    Toast.makeText(this, R.string.cache_cleared, Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("取消", (dialog, which) -> {
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (view.getId() == R.id.button_reset_database) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("重置数据");
            builder.setMessage("所有练习记录将被清空");
            builder.setPositiveButton("确认", (dialog, which) ->
                    DatabaseManager.resetDatabase(this));
            builder.setNegativeButton("取消", (dialog, which) -> {
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    //重写页面结束方法，用于刷新“我的”页面中的练习记录列表
    public void finish() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("pageNum", 2);//指定ViewPager的页面为“我的”
        startActivity(intent);
        super.finish();
    }
}
