package com.bupt.inklue.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bupt.data.api.PracticeLogApi;
import com.bupt.data.pojo.HanZi;
import com.bupt.data.pojo.Practice;
import com.bupt.evaluate.core.Evaluation;
import com.bupt.evaluate.core.Evaluator;
import com.bupt.inklue.R;
import com.bupt.inklue.adapter.EvaluateCardAdapter;
import com.bupt.inklue.decoration.HanZiCardDecoration;
import com.bupt.inklue.util.BitmapHelper;
import com.bupt.inklue.util.DirectoryHelper;

//评价结果页面
public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private EvaluateCardAdapter adapter;//卡片适配器
    private Practice practice;//练习数据
    private boolean isFinished = false;//评价是否完成

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        //取得练习数据
        practice = (Practice) getIntent().getSerializableExtra(getString(R.string.practice_bundle));

        //设置练习标题
        TextView textView = findViewById(R.id.textview_title);
        textView.setText(practice.getName());

        //修改开始按钮文字
        Button button_start = findViewById(R.id.button_start);
        button_start.setText(R.string.save);

        //初始化RecyclerView
        initRecyclerView();

        //设置RecyclerView中项目的点击监听器为处理中提示
        adapter.setOnItemClickListener(position ->
                Toast.makeText(this, R.string.processing, Toast.LENGTH_SHORT).show());

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_start).setOnClickListener(this);

        //异步调用评价模块
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            for (int i = 0; i < practice.hanZiList.size(); i++) {
                //取得评价所需数据
                HanZi hanZi = practice.hanZiList.get(i);
                String name = hanZi.getName();
                String className = hanZi.getCode();
                Bitmap inputBmp = BitmapFactory.decodeFile(hanZi.getWrittenPath());
                Bitmap stdBmp = BitmapFactory.decodeFile(hanZi.getPath());
                //调用评价模块
                Evaluation evaluation = Evaluator.evaluate(name, className, inputBmp, stdBmp);
                //存储反馈图像
                String feedbackPath = DirectoryHelper.generateCacheJPG(this);
                BitmapHelper.saveBitmap(evaluation.outputBmp, feedbackPath);
                //更新汉字数据
                hanZi.setFeedbackPath(feedbackPath);
                hanZi.setScore(Integer.toString(evaluation.score));
                hanZi.setAdvice(evaluation.advice);
                //更新RecyclerView
                int position = i;
                handler.post(() -> adapter.update(practice.hanZiList, position));
            }
            isFinished = true;
            //设置RecyclerView中项目的点击监听器为启动评价查看页面
            adapter.setOnItemClickListener(this::startEvaluateActivity);
        }).start();
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_start) {
            if (isFinished) {
                //保存练习记录
                if (PracticeLogApi.savePracticeLog(this, practice)) {
                    Toast.makeText(this, R.string.save_success, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.save_error, Toast.LENGTH_SHORT).show();
                }
                //回退到主页面
                backToMainActivity();
            } else {
                Toast.makeText(this, R.string.processing, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //启动评价查看页面
    private void startEvaluateActivity(int position) {
        Intent intent = new Intent();
        intent.setClass(this, EvaluateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.practice_bundle), practice);
        bundle.putInt(getString(R.string.position_bundle), position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //回退到主页面
    private void backToMainActivity() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(getString(R.string.page_num_bundle), 2);//指定ViewPager的页面为“我的”
        startActivity(intent);
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_practice);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        HanZiCardDecoration decoration = new HanZiCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new EvaluateCardAdapter(this, practice.hanZiList);
        recyclerView.setAdapter(adapter);//设置卡片适配器
    }
}
