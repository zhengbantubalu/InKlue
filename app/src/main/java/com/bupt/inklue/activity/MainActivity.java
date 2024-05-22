package com.bupt.inklue.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bupt.inklue.R;
import com.bupt.inklue.adapter.ViewPagerAdapter;
import com.bupt.inklue.data.DatabaseManager;
import com.bupt.inklue.data.FileManager;
import com.bupt.inklue.fragment.PracticeFragment;
import com.bupt.inklue.fragment.SearchFragment;
import com.bupt.inklue.fragment.UserFragment;

import java.util.ArrayList;

//主页面
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private ViewPager2 viewpager;//用于切换页面的类
    private ViewPagerAdapter adapter;//页面适配器
    private SearchFragment searchFragment;//“搜索”碎片
    private PracticeFragment practiceFragment;//“练习”碎片
    private UserFragment userFragment;//“我的”碎片
    private SharedPreferences sharedPreferences;//用于访问偏好设置的类

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //判断App是否为首次启动
        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        boolean isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true);
        if (isFirstLaunch) {
            //初始化App
            initApp();
        } else {
            //初始化ViewPager
            initViewPager();
        }
        //设置底部导航栏RadioGroup的选中变化监听器
        RadioGroup bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setOnCheckedChangeListener(this);
    }

    //当前页面取得新请求的回调
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int pageNum = intent.getIntExtra("pageNum", 1);
        if (pageNum == 0) {
            searchFragment.updateData();
        } else if (pageNum == 2) {
            userFragment.updateData();
        } else {
            practiceFragment.updateData();
        }
        adapter.notifyItemChanged(pageNum);
        viewpager.setCurrentItem(pageNum, true);
    }

    //初始化App
    private void initApp() {
        //隐藏底部导航栏，避免点击闪退
        findViewById(R.id.bottom_bar).setVisibility(View.GONE);
        //初始化目录
        FileManager.initDirectory(this);
        //异步下载资源图片
        Toast.makeText(this, R.string.downloading, Toast.LENGTH_SHORT).show();
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            //下载资源图片
            FileManager.downloadImg(this);
            handler.post(() -> {
                //重置数据库
                DatabaseManager.resetDatabase(this);
                //初始化ViewPager
                initViewPager();
                //标记App为非首次启动
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isFirstLaunch", false);
                editor.apply();
                //显示底部导航栏
                findViewById(R.id.bottom_bar).setVisibility(View.VISIBLE);
            });
        }).start();
    }

    //初始化ViewPager
    private void initViewPager() {
        viewpager = findViewById(R.id.viewpager_homepage);
        searchFragment = new SearchFragment();
        practiceFragment = new PracticeFragment();
        userFragment = new UserFragment();
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(searchFragment);
        fragments.add(practiceFragment);
        fragments.add(userFragment);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewpager.setAdapter(adapter);
        //ViewPager监听到页面切换后将对应的RadioButton设为选中状态
        //用于解决滑动页面后底部导航栏的RadioButton的选中反馈问题
        viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            public void onPageSelected(int position) {
                if (position == 0) {
                    ((RadioButton) findViewById(R.id.button_search)).setChecked(true);
                } else if (position == 1) {
                    ((RadioButton) findViewById(R.id.button_practice)).setChecked(true);
                } else if (position == 2) {
                    ((RadioButton) findViewById(R.id.button_user)).setChecked(true);
                }
            }
        });
        //将ViewPager的初始页面设为中间页面
        viewpager.setCurrentItem(1, false);
    }

    //RadioGroup选项切换后的回调方法
    //如果点击的RadioButton不对应ViewPager当前页面，就将ViewPager设为所选页面，并取消滚动动画
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if (checkedId == R.id.button_search && viewpager.getCurrentItem() != 0) {
            viewpager.setCurrentItem(0, false);
        } else if (checkedId == R.id.button_practice && viewpager.getCurrentItem() != 1) {
            viewpager.setCurrentItem(1, false);
        } else if (checkedId == R.id.button_user && viewpager.getCurrentItem() != 2) {
            viewpager.setCurrentItem(2, false);
        }
    }
}
