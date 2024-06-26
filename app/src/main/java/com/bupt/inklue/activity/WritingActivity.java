package com.bupt.inklue.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bupt.inklue.R;
import com.bupt.inklue.adapter.ViewPagerAdapter;
import com.bupt.inklue.data.CharData;
import com.bupt.inklue.data.PracticeData;
import com.bupt.inklue.fragment.FinishFragment;
import com.bupt.inklue.fragment.ImageFragment;
import com.bupt.inklue.util.ResourceDecoder;

import java.util.ArrayList;

//书写页面
public class WritingActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager2 viewpager;//用于切换图片的类
    private PracticeData practiceData;//练习数据

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏系统顶部状态栏
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);

        //取得练习数据
        practiceData = (PracticeData) getIntent().getSerializableExtra("practiceData");

        //初始化ViewPager
        initViewPager();

        //设置ViewPager当前位置
        int position = getIntent().getIntExtra("position", 0);
        viewpager.setCurrentItem(position, false);

        //设置ViewPager的选中位置监听器，用于判断是否滑动到最后一页
        viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            public void onPageSelected(int position) {
                if (position == practiceData.charsData.size()) {
                    //滑动到最后一页，则尝试启动相机
                    checkCameraPermission();
                }
            }
        });

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
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
        } else {
            //权限申请失败，将ViewPager回退至前一页
            viewpager.setCurrentItem(practiceData.charsData.size() - 1, true);
        }
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

    //启动拍照页面
    private void startCameraActivity() {
        Intent intent = new Intent();
        intent.setClass(this, CameraActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("practiceData", practiceData);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    //初始化ViewPager
    private void initViewPager() {
        viewpager = findViewById(R.id.viewpager_image);
        //设置图像向上偏移状态栏高度的一半，以实现图像居中
        int statusBarHeight = ResourceDecoder.getStatusBarHeight(this);
        viewpager.setTranslationY((float) -statusBarHeight / 2);
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (CharData charData : practiceData.charsData) {
            fragments.add(new ImageFragment(charData));
        }
        //添加结束页面
        fragments.add(new FinishFragment());
        viewpager.setAdapter(new ViewPagerAdapter(
                getSupportFragmentManager(), getLifecycle(), fragments));
    }
}
