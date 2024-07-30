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
import com.bupt.inklue.data.PracticeData;
import com.bupt.inklue.fragment.CheckFragment;
import com.bupt.inklue.fragment.FinishFragment;
import com.bupt.inklue.util.ResourceDecoder;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

//图像检查页面
public class CheckActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager2 viewpager;//用于切换图片的类
    private ViewPagerAdapter adapter;//页面适配器
    private PracticeData practiceData;//练习数据
    private int position;//当前图片在列表中的位置

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏系统顶部状态栏
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);

        //显示重拍按钮
        findViewById(R.id.button_reshot).setVisibility(View.VISIBLE);

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
        findViewById(R.id.button_reshot).setOnClickListener(this);
    }

    //ViewPager的翻页监听器，用于还原PhotoView的缩放状态和启动评价结果页面
    class Callback extends ViewPager2.OnPageChangeCallback {
        public void onPageSelected(int position) {
            //size是图片数量，由于结束页面的存在，ViewPager的实际页数为size+1
            int size = practiceData.charsData.size();
            //还原前后两页PhotoView的缩放状态
            if (position > 0) {
                PhotoView photoView =
                        ((CheckFragment) adapter.createFragment(position - 1)).photoView;
                if (photoView != null) {
                    photoView.setScale(1f, true);
                }
            }
            if (position < size - 1) {
                PhotoView photoView =
                        ((CheckFragment) adapter.createFragment(position + 1)).photoView;
                if (photoView != null) {
                    photoView.setScale(1f, true);
                }
            }
            //滑动到最后一页，则隐藏重拍按钮，并启动评价结果页面
            if (position == size) {
                findViewById(R.id.button_reshot).setVisibility(View.GONE);
                startResultActivity();
            }
        }
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_reshot) {
            Intent intent = new Intent();
            intent.setClass(this, ReshotActivity.class);
            Bundle bundle = new Bundle();
            position = viewpager.getCurrentItem();
            bundle.putSerializable("charData", practiceData.charsData.get(position));
            intent.putExtras(bundle);
            startActivityForResult(intent, Activity.RESULT_FIRST_USER);
        }
    }

    //关闭页面
    public void finish() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("practiceData", practiceData);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_FIRST_USER, intent);
        super.finish();
    }

    //子页面关闭的回调
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_FIRST_USER) {
            CharData charData = (CharData) intent.getSerializableExtra("charData");
            if (charData != null) {
                //更新图像
                CheckFragment fragment = (CheckFragment) adapter.createFragment(position);
                fragment.update(charData);
                adapter.notifyItemChanged(position);
                practiceData.charsData.set(position, charData);
                //关闭页面，提高操作流畅度
                finish();
            }
        }
    }

    //启动评价结果页面
    private void startResultActivity() {
        //结束确认页面
        ConfirmActivity.confirmActivity.finish();
        Intent intent = new Intent();
        intent.setClass(this, ResultActivity.class);
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
            fragments.add(new CheckFragment(charData));
        }
        //添加结束页面
        FinishFragment finishFragment = new FinishFragment();
        //设置结束页面文字
        finishFragment.setText(getString(R.string.confirm));
        fragments.add(finishFragment);
        //设置页面适配器
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewpager.setAdapter(adapter);
    }
}
