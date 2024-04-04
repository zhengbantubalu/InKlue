package com.bupt.inklue.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bupt.inklue.R;
import com.bupt.inklue.adapter.PageAdapter;
import com.bupt.inklue.data.CardData;
import com.bupt.inklue.data.CardsData;
import com.bupt.inklue.fragment.EvaluateFragment;

import java.util.ArrayList;

//评价页面
public class EvaluateActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager2 viewpager;//用于切换图片的类
    private CardsData imageCardsData;//图像卡片数据列表

    @SuppressWarnings("unchecked")//忽略取得图像卡片数据时类型转换产生的警告
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏系统顶部状态栏
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);

        //取得图像卡片数据
        imageCardsData = new CardsData((ArrayList<CardData>)
                (getIntent().getSerializableExtra("imageCardsData")));

        //初始化ViewPager
        initViewPager();

        //设置ViewPager当前位置
        int position = getIntent().getIntExtra("position", 0);
        viewpager.setCurrentItem(position, false);

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
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
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (CardData cardData : imageCardsData) {
            fragments.add(new EvaluateFragment(cardData));
        }
        viewpager.setAdapter(new PageAdapter(
                getSupportFragmentManager(), getLifecycle(), fragments));
    }
}
