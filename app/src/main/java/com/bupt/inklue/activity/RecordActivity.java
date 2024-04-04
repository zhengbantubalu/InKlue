package com.bupt.inklue.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bupt.inklue.R;
import com.bupt.inklue.adapter.CharCardDecoration;
import com.bupt.inklue.adapter.RecordCardAdapter;
import com.bupt.inklue.data.CardData;
import com.bupt.inklue.data.CardsData;
import com.bupt.inklue.data.DatabaseHelper;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener {

    private RecordCardAdapter adapter;//卡片适配器
    private CardData recordCardData;//记录数据
    private String charIDs;//汉字ID列表
    private CardsData charCardsData;//汉字卡片数据

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        //隐藏底部栏
        findViewById(R.id.bottom_bar).setVisibility(View.GONE);

        //取得记录数据
        getRecordData();

        //设置练习标题
        TextView textView = findViewById(R.id.textview_title);
        textView.setText(recordCardData.getName());

        //取得汉字卡片数据
        getCardsData();

        //初始化RecyclerView
        initRecyclerView();

        //RecyclerView中项目的点击监听器
        adapter.setOnItemClickListener(this::startEvaluateActivity);

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        }
    }

    //取得记录数据
    private void getRecordData() {
        recordCardData = new CardData();
        try (DatabaseHelper dbHelper = new DatabaseHelper(this)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long id = getIntent().getLongExtra("recordCardID", 0);
            Cursor cursor = db.rawQuery("SELECT * FROM Record WHERE id = " + id, null);
            int nameIndex = cursor.getColumnIndex("name");
            int charIDsIndex = cursor.getColumnIndex("charIDs");
            if (cursor.moveToFirst()) {
                recordCardData.setName(cursor.getString(nameIndex));
                charIDs = cursor.getString(charIDsIndex);
            }
            cursor.close();
        }
    }

    //取得汉字卡片数据
    private void getCardsData() {
        charCardsData = new CardsData();
        String[] idArray = charIDs.split(",");
        try (DatabaseHelper dbHelper = new DatabaseHelper(this)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (String id : idArray) {
                Cursor cursor = db.rawQuery("SELECT * FROM WrittenChar WHERE id = " +
                        id, null);
                int nameIndex = cursor.getColumnIndex("name");
                int writtenImgPathIndex = cursor.getColumnIndex("writtenImgPath");
                int scoreIndex = cursor.getColumnIndex("score");
                int adviceIndex = cursor.getColumnIndex("advice");
                if (cursor.moveToFirst()) {
                    do {
                        CardData cardData = new CardData();
                        cardData.setID(Long.parseLong(id));
                        cardData.setName(cursor.getString(nameIndex));
                        cardData.setWrittenImgPath(cursor.getString(writtenImgPathIndex));
                        cardData.setScore(cursor.getString(scoreIndex));
                        cardData.setAdvice(cursor.getString(adviceIndex));
                        charCardsData.add(cardData);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
            db.close();
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

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_practice);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        CharCardDecoration decoration = new CharCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new RecordCardAdapter(this, charCardsData);
        recyclerView.setAdapter(adapter);//设置卡片适配器
    }
}
