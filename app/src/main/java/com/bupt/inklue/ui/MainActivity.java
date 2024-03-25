package com.bupt.inklue.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bupt.inklue.R;
import com.bupt.inklue.adapter.HomepageAdapter;

import java.util.ArrayList;

//主页面
public class MainActivity extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener,
        View.OnClickListener {

    ViewPager2 viewpager_homepage;//用于切换页面的类

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioGroup bottomBar = findViewById(R.id.bottomBar);
        viewpager_homepage = findViewById(R.id.viewpager_homepage);
        //设置和添加Button的点击监听器，详见onClick方法
        findViewById(R.id.button_settings).setOnClickListener(this);
        findViewById(R.id.button_add).setOnClickListener(this);
        //底部导航栏RadioGroup的点击监听器，详见onCheckedChanged方法
        bottomBar.setOnCheckedChangeListener(this);
        //ViewPager监听到页面切换后将对应的RadioButton设为选中状态
        //用于解决滑动页面后底部导航栏的RadioButton的选中反馈问题
        viewpager_homepage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            public void onPageSelected(int position) {
                if (position == 0) {
                    ((RadioButton) findViewById(R.id.button_search)).setChecked(true);
                } else if (position == 1) {
                    ((RadioButton) findViewById(R.id.button_practise)).setChecked(true);
                } else if (position == 2) {
                    ((RadioButton) findViewById(R.id.button_user)).setChecked(true);
                }
            }
        });
        //初始化ViewPager
        initPager();
    }

    //初始化ViewPager
    private void initPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new PractiseFragment());
        fragments.add(new PractiseFragment());
        fragments.add(new UserFragment());
        HomepageAdapter homepageAdapter = new HomepageAdapter(
                getSupportFragmentManager(), getLifecycle(), fragments);
        viewpager_homepage.setAdapter(homepageAdapter);
        viewpager_homepage.setCurrentItem(1, false);//将初始页面设为中间页面，并取消滚动动画
    }

    //RadioGroup选项切换后的回调方法
    //如果点击的RadioButton不对应ViewPager当前页面，就将ViewPager设为所选页面，并取消滚动动画
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if (checkedId == R.id.button_search && viewpager_homepage.getCurrentItem() != 0) {
            viewpager_homepage.setCurrentItem(0, false);
        } else if (checkedId == R.id.button_practise && viewpager_homepage.getCurrentItem() != 1) {
            viewpager_homepage.setCurrentItem(1, false);
        } else if (checkedId == R.id.button_user && viewpager_homepage.getCurrentItem() != 2) {
            viewpager_homepage.setCurrentItem(2, false);
        }
    }

    //按下按钮的回调方法
    public void onClick(View view) {
    }
}