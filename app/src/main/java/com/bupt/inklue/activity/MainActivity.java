package com.bupt.inklue.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bupt.inklue.R;
import com.bupt.inklue.adapter.ViewPagerAdapter;
import com.bupt.inklue.fragment.PracticeFragment;
import com.bupt.inklue.fragment.SearchFragment;
import com.bupt.inklue.fragment.UserFragment;

import java.util.ArrayList;

//主页面
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private ViewPager2 viewpager;//用于切换页面的类
    private PracticeFragment practiceFragment;//“练习”碎片
    private UserFragment userFragment;//“我的”碎片

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //加载OpenCV
        System.loadLibrary("opencv_java3");

        //判断App是否为首次启动
        SharedPreferences preferences = getSharedPreferences("app", MODE_PRIVATE);
        boolean isFirstLaunch = preferences.getBoolean("isFirstLaunch", true);
        if (isFirstLaunch) {
            //启动初始化页面，并关闭当前页面
            Intent intent = new Intent();
            intent.setClass(this, InitActivity.class);
            startActivity(intent);
            finish();
        }

        //初始化ViewPager
        initViewPager();

        //设置底部导航栏RadioGroup的选中变化监听器
        RadioGroup bottom_bar = findViewById(R.id.bottom_bar);
        bottom_bar.setOnCheckedChangeListener(this);
    }

    //ViewPager的翻页监听器
    //监听到页面切换后将对应的RadioButton设为选中状态
    //用于解决滑动页面后底部导航栏的RadioButton的选中反馈问题
    class Callback extends ViewPager2.OnPageChangeCallback {
        public void onPageSelected(int position) {
            if (position == 0) {
                ((RadioButton) findViewById(R.id.button_search)).setChecked(true);
            } else if (position == 1) {
                ((RadioButton) findViewById(R.id.button_practice)).setChecked(true);
            } else if (position == 2) {
                ((RadioButton) findViewById(R.id.button_user)).setChecked(true);
            }
        }
    }

    //RadioGroup选项切换回调
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

    //当前页面取得新请求的回调
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int pageNum = intent.getIntExtra("pageNum", 1);
        practiceFragment.updateData();
        userFragment.updateData();
        viewpager.setCurrentItem(pageNum, true);
    }

    //初始化ViewPager
    private void initViewPager() {
        //取得视图
        viewpager = findViewById(R.id.viewpager_homepage);
        //创建页面
        practiceFragment = new PracticeFragment();
        SearchFragment searchFragment = new SearchFragment(practiceFragment);
        userFragment = new UserFragment(practiceFragment);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(searchFragment);
        fragments.add(practiceFragment);
        fragments.add(userFragment);
        //设置页面适配器
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager(), getLifecycle(), fragments);
        viewpager.setAdapter(adapter);
        //设置当前位置
        viewpager.setCurrentItem(1, false);
        //设置翻页监听器
        viewpager.registerOnPageChangeCallback(new Callback());
    }
}
