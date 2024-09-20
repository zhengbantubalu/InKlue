package com.bupt.inklue.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bupt.data.pojo.HanZi;
import com.bupt.data.pojo.Practice;
import com.bupt.inklue.R;
import com.bupt.inklue.adapter.ViewPagerAdapter;
import com.bupt.inklue.fragment.EvaluateFragment;
import com.bupt.inklue.util.BitmapHelper;
import com.bupt.inklue.util.Constants;
import com.bupt.inklue.util.ResourceHelper;
import com.github.chrisbanes.photoview.PhotoView;

import org.opencv.core.Scalar;

import java.util.ArrayList;

//评价查看页面
public class EvaluateActivity extends AppCompatActivity
        implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private ViewPagerAdapter adapter;//页面适配器
    private RadioButton button_feedback_img;//反馈按钮
    private ImageView imageview_top;//重影视图
    private Practice practice;//练习数据
    private int currentPosition;//ViewPager当前位置

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏系统顶部状态栏
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_evaluate);

        //取得视图
        button_feedback_img = findViewById(R.id.button_feedback_img);
        imageview_top = findViewById(R.id.imageview_top);

        //关闭硬件加速
        imageview_top.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        //取得练习数据
        practice = (Practice) getIntent().getSerializableExtra(getString(R.string.practice_bundle));

        //初始化ViewPager
        initViewPager();

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);

        //设置RadioGroup的选中变化监听器
        RadioGroup bottom_bar = findViewById(R.id.bottom_bar);
        bottom_bar.setOnCheckedChangeListener(this);
    }

    //ViewPager的翻页监听器
    class Callback extends ViewPager2.OnPageChangeCallback {
        public void onPageSelected(int position) {
            PhotoView photoView = ((EvaluateFragment) adapter.createFragment(currentPosition)).photoView;
            if (photoView != null) {
                //移除重影图
                imageview_top.setImageBitmap(null);
                //还原缩放状态
                photoView.setScale(1f, true);
                //获取图片用于还原
                HanZi hanZi = practice.hanZiList.get(currentPosition);
                Bitmap bitmap = BitmapFactory.decodeFile(hanZi.getFeedbackPath());
                //等待缩放动画结束
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    photoView.setImageBitmap(bitmap);//还原图片
                    button_feedback_img.setChecked(true);//还原RadioGroup选项
                }, Constants.ANIMATION_TIME);
            }
            currentPosition = position;
        }
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        }
    }

    //RadioGroup选项切换回调
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        HanZi hanZi = practice.hanZiList.get(currentPosition);
        PhotoView photoView = ((EvaluateFragment) adapter.createFragment(currentPosition)).photoView;
        Bitmap bitmap = null;
        if (checkedId == R.id.button_standard_img) {
            imageview_top.setImageBitmap(null);//移除重影图
            bitmap = BitmapFactory.decodeFile(hanZi.getPath());
        } else if (checkedId == R.id.button_written_img) {
            imageview_top.setImageBitmap(null);//移除重影图
            bitmap = BitmapFactory.decodeFile(hanZi.getWrittenPath());
        } else if (checkedId == R.id.button_feedback_img) {
            imageview_top.setImageBitmap(null);//移除重影图
            bitmap = BitmapFactory.decodeFile(hanZi.getFeedbackPath());
        } else if (checkedId == R.id.button_overlap_img) {
            bitmap = BitmapFactory.decodeFile(hanZi.getWrittenPath());
            //显示重影图
            Scalar color = ResourceHelper.getScalar(this, R.attr.colorTheme);
            Bitmap topBitmap = BitmapHelper.toTransparent(hanZi.getPath(), color);
            imageview_top.setImageBitmap(topBitmap);
        }
        if (bitmap != null) {
            photoView.setImageBitmap(bitmap);
        }
    }

    //初始化ViewPager
    private void initViewPager() {
        //取得视图
        ViewPager2 viewpager = findViewById(R.id.viewpager_image);
        //向上偏移状态栏高度的一半，以实现居中
        int statusBarHeight = ResourceHelper.getStatusBarHeight(this);
        viewpager.setTranslationY((float) -statusBarHeight / 2);
        imageview_top.setTranslationY((float) -statusBarHeight / 2);//重影图同时上移
        //创建页面
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (HanZi hanZi : practice.hanZiList) {
            fragments.add(new EvaluateFragment(hanZi));
        }
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
