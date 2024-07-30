package com.bupt.inklue.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bupt.inklue.R;
import com.bupt.inklue.adapter.ViewPagerAdapter;
import com.bupt.inklue.data.CharData;
import com.bupt.inklue.data.PracticeData;
import com.bupt.inklue.fragment.EvaluateFragment;
import com.bupt.inklue.util.ResourceDecoder;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

//评价查看页面
public class EvaluateActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager2 viewpager;//用于切换图片的类
    private ViewPagerAdapter adapter;//页面适配器
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

        //设置ViewPager的翻页监听器
        viewpager.registerOnPageChangeCallback(new Callback());

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
    }

    //ViewPager的翻页监听器，用于还原PhotoView的缩放状态
    class Callback extends ViewPager2.OnPageChangeCallback {
        public void onPageSelected(int position) {
            int size = practiceData.charsData.size();
            //还原前后两页PhotoView的缩放状态
            if (position > 0) {
                PhotoView photoView =
                        ((EvaluateFragment) adapter.createFragment(position - 1)).photoView;
                if (photoView != null) {
                    photoView.setScale(1f, true);
                }
            }
            if (position < size - 1) {
                PhotoView photoView =
                        ((EvaluateFragment) adapter.createFragment(position + 1)).photoView;
                if (photoView != null) {
                    photoView.setScale(1f, true);
                }
            }
        }
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        }
    }

    //初始化ViewPager
    private void initViewPager() {
        viewpager = findViewById(R.id.viewpager_image);
        //设置图像向上偏移状态栏高度的一半，以实现图像居中
        int statusBarHeight = ResourceDecoder.getStatusBarHeight(this);
        viewpager.setTranslationY((float) -statusBarHeight / 2);
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (CharData charData : practiceData.charsData) {
            fragments.add(new EvaluateFragment(charData));
        }
        //设置页面适配器
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewpager.setAdapter(adapter);
    }
}
