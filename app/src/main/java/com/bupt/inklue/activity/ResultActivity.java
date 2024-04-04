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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bupt.evaluate.core.Evaluation;
import com.bupt.evaluate.core.Evaluator;
import com.bupt.inklue.R;
import com.bupt.inklue.adapter.EvaluateCardAdapter;
import com.bupt.inklue.adapter.CharCardDecoration;
import com.bupt.inklue.data.CardData;
import com.bupt.inklue.data.CardsData;
import com.bupt.inklue.util.BitmapProcessor;

import java.util.ArrayList;

//评价结果页面
public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private EvaluateCardAdapter adapter;//图像评价卡片适配器
    private CardsData imageCardsData = new CardsData();//图像卡片数据

    @SuppressWarnings("unchecked")//忽略取得图像卡片数据时类型转换产生的警告
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        //取得Intent数据
        imageCardsData = new CardsData((ArrayList<CardData>)
                (getIntent().getSerializableExtra("imageCardsData")));
        String practiceName = getIntent().getStringExtra("practiceName");

        //设置练习标题
        TextView textView = findViewById(R.id.title);
        textView.setText(practiceName);

        //修改开始按钮文字
        Button button_start = findViewById(R.id.button_start);
        button_start.setText(R.string.save);

        //初始化RecyclerView
        initRecyclerView();

        //RecyclerView中项目的点击监听器
        adapter.setOnItemClickListener(this::startEvaluateActivity);

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_start).setOnClickListener(this);

        //异步调用评价模块
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < imageCardsData.size(); i++) {
                    CardData cardData = imageCardsData.get(i);
                    String cnChar = cardData.getName();
                    Bitmap inputBmp = BitmapFactory.decodeFile(cardData.getWrittenImgPath());
                    Bitmap stdBmp = BitmapFactory.decodeFile(cardData.getStdImgPath());
                    Evaluation evaluation = Evaluator.evaluate(cnChar, inputBmp, stdBmp);
                    BitmapProcessor.save(evaluation.outputBmp, cardData.getWrittenImgPath());
                    cardData.setAdvice(evaluation.advice);
                    cardData.setScore(evaluation.score);
                    int position = i;
                    handler.post(new Runnable() {
                        public void run() {
                            adapter.update(imageCardsData, position);
                        }
                    });
                }
            }
        }).start();
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_start) {
            save();
        }
    }

    //启动评价页面
    private void startEvaluateActivity(int position) {
        Intent intent = new Intent();
        intent.setClass(this, EvaluateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("imageCardsData", imageCardsData);
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //保存练习记录
    private void save() {
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_practice_detail);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        CharCardDecoration decoration = new CharCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new EvaluateCardAdapter(this, imageCardsData);
        recyclerView.setAdapter(adapter);//设置图像卡片适配器
    }
}
