package com.bupt.inklue.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bupt.inklue.R;
import com.bupt.inklue.adapter.PageAdapter;
import com.bupt.inklue.fragment.PracticeFragment;
import com.bupt.inklue.fragment.SearchFragment;
import com.bupt.inklue.fragment.UserFragment;

import java.util.ArrayList;

//主页面
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private ViewPager2 viewpager;//用于切换页面的类

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化App
        initApp();

        //初始化ViewPager
        initViewPager();

        //将ViewPager的初始页面设为中间页面，并取消滚动动画
        viewpager.setCurrentItem(1, false);

        //设置底部导航栏RadioGroup的选中变化监听器，详见onCheckedChanged方法
        RadioGroup bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setOnCheckedChangeListener(this);
    }

    //初始化App
    private void initApp() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true);
        if (isFirstLaunch) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstLaunch", false);
            editor.apply();
        }
    }

    //初始化ViewPager
    private void initViewPager() {
        viewpager = findViewById(R.id.viewpager_homepage);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new SearchFragment());
        fragments.add(new PracticeFragment());
        fragments.add(new UserFragment());
        viewpager.setAdapter(new PageAdapter(
                getSupportFragmentManager(), getLifecycle(), fragments));
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
