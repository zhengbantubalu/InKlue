package com.bupt.inklue.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView textview_title;//练习标题
    private boolean needUpdate = false;//是否需要更新练习列表

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        //设置视图可见性
        findViewById(R.id.button_more).setVisibility(View.VISIBLE);

        //取得练习数据
        practiceData = (PracticeData) getIntent().getSerializableExtra("practiceData");
        if (practiceData != null) {
            practiceData.charsData = PracticeDataManager.getStdCharsData(this, practiceData);
        }

        //设置练习标题
        textview_title = findViewById(R.id.textview_title);
        textview_title.setText(practiceData.getName());

        //如果练习为空，隐藏底部栏
        if (practiceData.charsData.isEmpty()) {
            findViewById(R.id.bottom_bar).setVisibility(View.GONE);
        }

        //初始化RecyclerView
        initRecyclerView();

        //设置RecyclerView中项目的点击监听器
        adapter.setOnItemClickListener(this::startWritingActivity);

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_start).setOnClickListener(this);
        findViewById(R.id.button_more).setOnClickListener(this);
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_start) {
            tryToStartCamera();
        } else if (view.getId() == R.id.button_more) {
            openMenu();
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

    //权限申请的回调，用于在获取权限后继续刚才中断的操作
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && grantResults.length != 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //权限申请成功，继续启动拍照页面
            startCameraActivity();
        } else {
            //权限申请失败，提示给予权限
            Toast.makeText(this, R.string.give_camera_permission_hint, Toast.LENGTH_SHORT).show();
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

    //打开菜单
    private void openMenu() {
        PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.button_more));
        popupMenu.getMenuInflater().inflate(R.menu.menu_practice, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_item_rename) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.rename);
                final EditText input = new EditText(this);
                builder.setView(input);
                builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
                    practiceData.setName(input.getText().toString());
                    PracticeDataManager.renamePractice(
                            this, practiceData, practiceData.getName());
                    textview_title.setText(practiceData.getName());//设置练习标题
                    needUpdate = true;
                });
                builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            } else if (item.getItemId() == R.id.menu_item_delete) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.delete);
                builder.setMessage(R.string.delete_practice_warning);
                builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
                    PracticeDataManager.deletePractice(this, practiceData);
                    needUpdate = true;
                    finish();
                });
                builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    //尝试启动相机
    private void tryToStartCamera() {
        //检查相机权限
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
