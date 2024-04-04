package com.bupt.inklue.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bupt.inklue.R;
import com.bupt.inklue.adapter.ViewPagerAdapter;
import com.bupt.inklue.data.CardData;
import com.bupt.inklue.data.CardsData;
import com.bupt.inklue.fragment.CheckFragment;

import java.util.ArrayList;

//图片检查页面
public class CheckActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager2 viewpager;//用于切换图片的类
    private CardsData charCardsData;//汉字卡片数据列表
    private int position;//当前图片在列表中的位置
    private ViewPagerAdapter adapter;//ViewPager的适配器

    @SuppressWarnings("unchecked")//忽略取得图像卡片数据时类型转换产生的警告
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏系统顶部状态栏
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);

        //取得图像卡片数据
        charCardsData = new CardsData((ArrayList<CardData>)
                (getIntent().getSerializableExtra("charCardsData")));

        //初始化ViewPager
        initViewPager();

        //设置ViewPager当前位置
        int position = getIntent().getIntExtra("position", 0);
        viewpager.setCurrentItem(position, false);

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
        Button button_reshot = findViewById(R.id.button_reshot);
        button_reshot.setVisibility(View.VISIBLE);
        button_reshot.setOnClickListener(this);
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
            bundle.putSerializable("cardData", charCardsData.get(position));
            intent.putExtras(bundle);
            startActivityForResult(intent, Activity.RESULT_FIRST_USER);
        }
    }

    //关闭页面
    public void finish() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("charCardsData", charCardsData);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_FIRST_USER, intent);
        super.finish();
    }

    //子页面关闭的回调
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_FIRST_USER) {
            CardData cardData = (CardData) intent.getSerializableExtra("cardData");
            if (cardData != null) {
                //更新图像
                CheckFragment fragment = (CheckFragment) adapter.createFragment(position);
                fragment.update(cardData);
                adapter.notifyItemChanged(position);
                charCardsData.set(position, cardData);
            }
        }
    }

    //初始化ViewPager
    private void initViewPager() {
        viewpager = findViewById(R.id.viewpager_image);
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (CardData cardData : charCardsData) {
            fragments.add(new CheckFragment(cardData));
        }
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewpager.setAdapter(adapter);
    }
}
