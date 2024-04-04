package com.bupt.inklue.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import com.bupt.inklue.data.CardData;
import com.bupt.inklue.data.CardsData;
import com.bupt.inklue.data.DatabaseHelper;
import com.bupt.inklue.util.BitmapProcessor;

import java.util.ArrayList;

//评价结果页面
public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private EvaluateCardAdapter adapter;//卡片适配器
    private CardsData charCardsData;//汉字卡片数据
    private CardData practiceCardData;//练习数据

    @SuppressWarnings("unchecked")//忽略取得图像卡片数据时类型转换产生的警告
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        //取得Intent数据
        charCardsData = new CardsData((ArrayList<CardData>)
                (getIntent().getSerializableExtra("charCardsData")));
        practiceCardData = (CardData) getIntent().getSerializableExtra("practiceCardData");

        //设置练习标题
        TextView textView = findViewById(R.id.textview_title);
        textView.setText(practiceCardData.getName());

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
        new Thread(() -> {
            for (int i = 0; i < charCardsData.size(); i++) {
                CardData cardData = charCardsData.get(i);
                String cnChar = cardData.getName();
                Bitmap inputBmp = BitmapFactory.decodeFile(cardData.getWrittenImgPath());
                Bitmap stdBmp = BitmapFactory.decodeFile(cardData.getStdImgPath());
                Evaluation evaluation = Evaluator.evaluate(cnChar, inputBmp, stdBmp);
                BitmapProcessor.save(evaluation.outputBmp, cardData.getWrittenImgPath());
                cardData.setScore(Integer.toString(evaluation.score));
                cardData.setAdvice(evaluation.advice);
                int position = i;
                handler.post(() -> adapter.update(charCardsData, position));
            }
        }).start();
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_start) {
            saveRecord();
        }
    }

    //启动评价页面
    private void startEvaluateActivity(int position) {
        Intent intent = new Intent();
        intent.setClass(this, EvaluateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("charCardsData", charCardsData);
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

    //保存练习记录
    private void saveRecord() {
        int successNum = 0;
        try (DatabaseHelper dbHelper = new DatabaseHelper(this)) {
            StringBuilder CharIDs = new StringBuilder();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //保存汉字卡片
            for (CardData cardData : charCardsData) {
                ContentValues values = new ContentValues();
                values.put("name", cardData.getName());
                values.put("stdImgPath", cardData.getStdImgPath());
                values.put("writtenImgPath", cardData.getWrittenImgPath());
                values.put("score", cardData.getScore());
                values.put("advice", cardData.getAdvice());
                long newID = db.insert("WrittenChar", null, values);
                if (newID != -1) {
                    successNum++;
                    CharIDs.append(newID).append(",");
                }
            }
            CharIDs.deleteCharAt(CharIDs.length() - 1);//移除最后一个逗号
            //保存练习卡片
            ContentValues values = new ContentValues();
            values.put("name", practiceCardData.getName());
            values.put("coverImgPath", practiceCardData.getStdImgPath());
            values.put("charIDs", CharIDs.toString());
            long newID = db.insert("Record", null, values);
            if (newID != -1) {
                successNum++;
            }
            db.close();
        }
        //显示保存反馈信息
        if (successNum == charCardsData.size() + 1) {
            Toast.makeText(this, R.string.save_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.save_error, Toast.LENGTH_SHORT).show();
        }
        //回退到主页面
        backToMainActivity();
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_practice);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        CharCardDecoration decoration = new CharCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new EvaluateCardAdapter(this, charCardsData);
        recyclerView.setAdapter(adapter);
    }
}
