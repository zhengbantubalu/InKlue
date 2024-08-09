package com.bupt.inklue.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

    private ViewPagerAdapter adapter;//页面适配器
    private ArrayList<CharData> charsData;//汉字数据列表
    private int currentPosition;//ViewPager当前位置
    private boolean needUpdate = false;//是否需要更新练习列表

    @SuppressWarnings("unchecked")//忽略取得汉字数据时类型转换产生的警告
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏系统顶部状态栏
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);

        //取得视图
        ImageButton button_add = findViewById(R.id.button_add);
        Button button_one_shot = findViewById(R.id.button_reshot);

        //设置视图可见性
        button_add.setVisibility(View.VISIBLE);
        button_one_shot.setVisibility(View.VISIBLE);

        //修改单拍按钮文字
        button_one_shot.setText(R.string.oneshot);

        //取得汉字数据列表
        charsData = (ArrayList<CharData>) getIntent().getSerializableExtra("charsData");

        //初始化ViewPager
        initViewPager();

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
        button_add.setOnClickListener(this);
        button_one_shot.setOnClickListener(this);
    }

    //ViewPager的翻页监听器，用于还原PhotoView的缩放状态
    class Callback extends ViewPager2.OnPageChangeCallback {
        public void onPageSelected(int position) {
            PhotoView photoView = ((ImageFragment) adapter.createFragment(currentPosition)).photoView;
            if (photoView != null) {
                //还原缩放状态
                photoView.setScale(1f, true);
            }
            currentPosition = position;
        }
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_add) {
            startSelectActivity();
        } else if (view.getId() == R.id.button_reshot) {
            tryToStartCamera();
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

    //权限申请的回调，用于在获取权限后继续刚才中断的操作
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && grantResults.length != 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //权限申请成功，继续启动单拍页面
            startOneshotActivity();
        } else {
            //权限申请失败，提示给予权限
            Toast.makeText(this, R.string.give_camera_permission_hint, Toast.LENGTH_SHORT).show();
        }
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
        CharData charData = charsData.get(currentPosition);
        bundle.putLong("charID", charData.getID());
        bundle.putString("charImgPath", charData.getStdImgPath());
        intent.putExtras(bundle);
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }

    //启动单拍页面
    private void startOneshotActivity() {
        Intent intent = new Intent();
        intent.setClass(this, OneshotActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("charData", charsData.get(currentPosition));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //尝试启动相机
    private void tryToStartCamera() {
        //检查相机权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            //拥有权限，启动单拍页面
            startOneshotActivity();
        } else {
            //无权限则申请
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 0);
        }
    }

    //初始化ViewPager
    private void initViewPager() {
        //取得视图
        ViewPager2 viewpager = findViewById(R.id.viewpager_image);
        //设置图像向上偏移状态栏高度的一半，以实现图像居中
        int statusBarHeight = ResourceDecoder.getStatusBarHeight(this);
        viewpager.setTranslationY((float) -statusBarHeight / 2);
        //创建页面
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (CharData charData : charsData) {
            fragments.add(new ImageFragment(charData));
        }
        //设置页面适配器
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewpager.setAdapter(adapter);
        //设置当前位置
        int position = getIntent().getIntExtra("position", 0);
        viewpager.setCurrentItem(position, false);
        currentPosition = position;
        //设置翻页监听器
        viewpager.registerOnPageChangeCallback(new Callback());
    }
}
