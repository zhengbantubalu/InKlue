package com.bupt.inklue.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bupt.inklue.R;
import com.bupt.inklue.activity.PracticeActivity;
import com.bupt.inklue.adapter.PracticeCardAdapter;
import com.bupt.inklue.adapter.PracticeCardDecoration;
import com.bupt.inklue.data.DatabaseHelper;
import com.bupt.inklue.data.PracticeData;

import java.util.ArrayList;

//“练习”碎片
public class PracticeFragment extends Fragment {

    private View root;//根视图
    private Context context;//环境
    private PracticeCardAdapter adapter;//卡片适配器
    private ArrayList<PracticeData> practicesData;//练习数据列表

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_practice, container, false);
            context = getContext();

            //取得练习数据列表
            practicesData = new ArrayList<>();
            getPracticesData();

            //初始化RecyclerView
            initRecyclerView();

            //设置RecyclerView中项目的点击监听器
            adapter.setOnItemClickListener(position -> {
                Intent intent = new Intent();
                intent.setClass(context, PracticeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("practiceData", practicesData.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            });

            //“添加”按钮的点击监听器
            ImageButton button_add = root.findViewById(R.id.button_add);
            button_add.setOnClickListener(view ->
                    Toast.makeText(context, R.string.developing, Toast.LENGTH_SHORT).show());
        }
        return root;
    }

    //更新数据
    @SuppressLint("NotifyDataSetChanged")//忽略更新具体数据的要求
    public void updateData() {
        if (practicesData != null) {
            practicesData.clear();
            getPracticesData();
            adapter.notifyDataSetChanged();
        }
    }

    //取得练习数据列表
    private void getPracticesData() {
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
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
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_practice);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        PracticeCardDecoration decoration = new PracticeCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new PracticeCardAdapter(context, practicesData);
        recyclerView.setAdapter(adapter);//设置卡片适配器
    }
}
