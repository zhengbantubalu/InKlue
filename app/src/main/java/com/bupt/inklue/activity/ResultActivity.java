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

import com.bupt.evaluate.core.Evaluation;
import com.bupt.evaluate.core.Evaluator;
import com.bupt.inklue.R;
import com.bupt.inklue.adapter.CharCardDecoration;
import com.bupt.inklue.adapter.EvaluateCardAdapter;
import com.bupt.inklue.data.CharData;
import com.bupt.inklue.data.PracticeData;
import com.bupt.inklue.data.PracticeDataManager;
import com.bupt.inklue.util.BitmapProcessor;
import com.bupt.inklue.util.FilePathGenerator;

//评价结果页面
public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private EvaluateCardAdapter adapter;//卡片适配器
    private PracticeData practiceData;//练习数据
    private boolean isFinished = false;//评价是否完成

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        //取得练习数据
        practiceData = (PracticeData) getIntent().getSerializableExtra("practiceData");

        //创建记录封面
        String coverImgPath = FilePathGenerator.generateCacheJPG(this);
        if (practiceData != null) {
            practiceData.setCoverImgPath(coverImgPath);
            BitmapProcessor.createCover(practiceData, practiceData.getCoverImgPath(), false);
        }

        //设置练习标题
        TextView textView = findViewById(R.id.textview_title);
        textView.setText(practiceData.getName());

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
            for (int i = 0; i < practiceData.charsData.size(); i++) {
                CharData charData = practiceData.charsData.get(i);
                String name = charData.getName();
                String className = charData.getClassName();
                Bitmap inputBmp = BitmapFactory.decodeFile(charData.getWrittenImgPath());
                Bitmap stdBmp = BitmapFactory.decodeFile(charData.getStdImgPath());
                Evaluation evaluation = Evaluator.evaluate(name, className, inputBmp, stdBmp);
                BitmapProcessor.save(evaluation.outputBmp, charData.getWrittenImgPath());
                charData.setScore(Integer.toString(evaluation.score));
                charData.setAdvice(evaluation.advice);
                int position = i;
                handler.post(() -> adapter.update(practiceData.charsData, position));
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
                PracticeDataManager.saveRecord(this, practiceData);
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
        bundle.putSerializable("practiceData", practiceData);
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //回退到主页面
    private void backToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("pageNum", 2);//指定ViewPager的页面为“我的”
        startActivity(intent);
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_practice);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        CharCardDecoration decoration = new CharCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new EvaluateCardAdapter(this, practiceData.charsData);
        recyclerView.setAdapter(adapter);//设置卡片适配器
    }
}
