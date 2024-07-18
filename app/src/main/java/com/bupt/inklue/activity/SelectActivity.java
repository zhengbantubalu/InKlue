package com.bupt.inklue.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bupt.inklue.R;
import com.bupt.inklue.adapter.PracticeCardAdapter;
import com.bupt.inklue.adapter.PracticeCardDecoration;
import com.bupt.inklue.data.DatabaseHelper;
import com.bupt.inklue.data.PracticeData;
import com.bupt.inklue.data.PracticeDataManager;

import java.util.ArrayList;
import java.util.Collections;

//选择页面，用于选择汉字要添加到的练习
public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    private PracticeCardAdapter adapter;//卡片适配器
    private ArrayList<PracticeData> practicesData;//练习数据列表
    private boolean needUpdate = false;//是否需要更新练习列表

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        //取得汉字ID与图像路径
        long charID = getIntent().getLongExtra("charID", 0);
        String charImgPath = getIntent().getStringExtra("charImgPath");

        //取得练习数据列表
        practicesData = new ArrayList<>();
        getPracticesData();

        //初始化RecyclerView
        initRecyclerView();

        //设置RecyclerView中项目的点击监听器
        adapter.setOnItemClickListener(position -> {
            //向练习中添加汉字
            PracticeDataManager.addCharIntoPractice(this, practicesData.get(position),
                    charID, charImgPath);
            needUpdate = true;
            finish();
        });

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
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

    //取得练习数据列表
    private void getPracticesData() {
        try (DatabaseHelper dbHelper = new DatabaseHelper(this)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("Practice", null, null,
                    null, null, null, null);
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int coverImgPathIndex = cursor.getColumnIndex("coverImgPath");
            int charIDsIndex = cursor.getColumnIndex("charIDs");
            if (cursor.moveToFirst()) {
                do {
                    PracticeData practiceData = new PracticeData();
                    practiceData.setID(cursor.getLong(idIndex));
                    practiceData.setName(cursor.getString(nameIndex));
                    practiceData.setCoverImgPath(cursor.getString(coverImgPathIndex));
                    practiceData.setCharIDs(cursor.getString(charIDsIndex));
                    practicesData.add(practiceData);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        Collections.reverse(practicesData);//反转练习数据列表
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_practice);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        PracticeCardDecoration decoration = new PracticeCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new PracticeCardAdapter(this, practicesData);
        recyclerView.setAdapter(adapter);//设置卡片适配器
    }
}
