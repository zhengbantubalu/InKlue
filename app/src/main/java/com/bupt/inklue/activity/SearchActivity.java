package com.bupt.inklue.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bupt.inklue.R;
import com.bupt.inklue.adapter.ViewPagerAdapter;
import com.bupt.inklue.data.CharData;
import com.bupt.inklue.fragment.ImageFragment;
import com.bupt.inklue.util.ResourceDecoder;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

//搜索结果查看页面
public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager2 viewpager;//用于切换图片的类
    private ViewPagerAdapter adapter;//页面适配器
    private ArrayList<CharData> charsData;//汉字数据列表
    private boolean needUpdate = false;//是否需要更新练习列表

    @SuppressWarnings("unchecked")//忽略取得汉字数据时类型转换产生的警告
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏系统顶部状态栏
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);

        //设置视图可见性
        findViewById(R.id.button_add).setVisibility(View.VISIBLE);

        //取得汉字数据列表
        charsData = (ArrayList<CharData>) getIntent().getSerializableExtra("charsData");

        //初始化ViewPager
        initViewPager();

        //设置ViewPager当前位置
        int position = getIntent().getIntExtra("position", 0);
        viewpager.setCurrentItem(position, false);

        //设置ViewPager的翻页监听器
        viewpager.registerOnPageChangeCallback(new Callback());

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_add).setOnClickListener(this);
    }

    //ViewPager的翻页监听器，用于还原PhotoView的缩放状态
    class Callback extends ViewPager2.OnPageChangeCallback {
        public void onPageSelected(int position) {
            int size = charsData.size();
            //还原前后两页PhotoView的缩放状态
            if (position > 0) {
                PhotoView photoView =
                        ((ImageFragment) adapter.createFragment(position - 1)).photoView;
                if (photoView != null) {
                    photoView.setScale(1f, true);
                }
            }
            if (position < size - 1) {
                PhotoView photoView =
                        ((ImageFragment) adapter.createFragment(position + 1)).photoView;
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
        } else if (view.getId() == R.id.button_add) {
            startSelectActivity();
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

    //子页面关闭的回调
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_FIRST_USER) {
            needUpdate = intent.getBooleanExtra("needUpdate", false);
            if (needUpdate) {
                //关闭页面，提高操作流畅度
                finish();
            }
        }
    }

    //启动选择页面
    private void startSelectActivity() {
        Intent intent = new Intent();
        intent.setClass(this, SelectActivity.class);
        Bundle bundle = new Bundle();
        CharData charData = charsData.get(viewpager.getCurrentItem());
        bundle.putLong("charID", charData.getID());
        bundle.putString("charImgPath", charData.getStdImgPath());
        intent.putExtras(bundle);
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }

    //初始化ViewPager
    private void initViewPager() {
        viewpager = findViewById(R.id.viewpager_image);
        //设置图像向上偏移状态栏高度的一半，以实现图像居中
        int statusBarHeight = ResourceDecoder.getStatusBarHeight(this);
        viewpager.setTranslationY((float) -statusBarHeight / 2);
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (CharData charData : charsData) {
            fragments.add(new ImageFragment(charData));
        }
        //设置页面适配器
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewpager.setAdapter(adapter);
    }
}
