package com.bupt.inklue.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bupt.data.pojo.HanZi;
import com.bupt.data.pojo.Practice;
import com.bupt.inklue.R;
import com.bupt.inklue.adapter.ViewPagerAdapter;
import com.bupt.inklue.fragment.CheckFragment;
import com.bupt.inklue.fragment.FinishFragment;
import com.bupt.inklue.util.ResourceHelper;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

//图像检查页面
public class CheckActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPagerAdapter adapter;//页面适配器
    private Practice practice;//练习数据
    private int currentPosition;//ViewPager当前位置

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
        practice = (Practice) getIntent().getSerializableExtra(getString(R.string.practice_bundle));

        //初始化ViewPager
        initViewPager();

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_reshot).setOnClickListener(this);
    }

    //ViewPager的翻页监听器，用于还原PhotoView的缩放状态和启动评价结果页面
    class Callback extends ViewPager2.OnPageChangeCallback {
        public void onPageSelected(int position) {
            PhotoView photoView = ((CheckFragment) adapter.createFragment(currentPosition)).photoView;
            if (photoView != null) {
                //还原缩放状态
                photoView.setScale(1f, true);
            }
            //滑动到最后一页，则隐藏重拍按钮，并启动评价结果页面
            //size是图片数量，由于结束页面的存在，ViewPager的实际页数为size+1
            if (position == practice.hanZiList.size()) {
                findViewById(R.id.button_reshot).setVisibility(View.GONE);
                startResultActivity();
            }
            currentPosition = position;
        }
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_reshot) {
            startReshotActivity();
        }
    }

    //关闭页面
    public void finish() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.practice_bundle), practice);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_FIRST_USER, intent);
        super.finish();
    }

    //子页面关闭的回调
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_FIRST_USER) {
            HanZi hanZi = (HanZi) intent.getSerializableExtra(getString(R.string.han_zi_bundle));
            if (hanZi != null) {
                //更新图像
                CheckFragment fragment = (CheckFragment) adapter.createFragment(currentPosition);
                fragment.update(hanZi);
                adapter.notifyItemChanged(currentPosition);
                practice.hanZiList.set(currentPosition, hanZi);
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
        bundle.putSerializable(getString(R.string.practice_bundle), practice);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    //启动重拍页面
    private void startReshotActivity() {
        Intent intent = new Intent();
        intent.setClass(this, ReshotActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.han_zi_bundle),
                practice.hanZiList.get(currentPosition));
        intent.putExtras(bundle);
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }

    //初始化ViewPager
    private void initViewPager() {
        //取得视图
        ViewPager2 viewpager = findViewById(R.id.viewpager_image);
        //设置图像向上偏移状态栏高度的一半，以实现图像居中
        int statusBarHeight = ResourceHelper.getStatusBarHeight(this);
        viewpager.setTranslationY((float) -statusBarHeight / 2);
        //创建页面
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (HanZi hanZi : practice.hanZiList) {
            fragments.add(new CheckFragment(hanZi));
        }
        //添加结束页面
        FinishFragment finishFragment = new FinishFragment();
        //设置结束页面文字
        finishFragment.setText(getString(R.string.confirm));
        fragments.add(finishFragment);
        //设置页面适配器
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewpager.setAdapter(adapter);
        //设置当前位置
        int position = getIntent().getIntExtra(getString(R.string.position_bundle), 0);
        viewpager.setCurrentItem(position, false);
        currentPosition = position;
        //设置翻页监听器
        viewpager.registerOnPageChangeCallback(new Callback());
    }
}
