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
import com.bupt.inklue.activity.SearchActivity;
import com.bupt.inklue.adapter.CharCardAdapter;
import com.bupt.inklue.adapter.CharCardDecoration;
import com.bupt.inklue.data.CharData;
import com.bupt.inklue.data.DatabaseHelper;

import java.util.ArrayList;

//“搜索”碎片
public class SearchFragment extends Fragment {

    private View root;//根视图
    private Context context;//环境
    private CharCardAdapter adapter;//卡片适配器
    private ArrayList<CharData> resultCharsData;//搜索结果汉字数据列表

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_search, container, false);
            context = getContext();

            //取得搜索结果汉字数据列表
            resultCharsData = new ArrayList<>();
            getResultCharsData();

            //初始化RecyclerView
            initRecyclerView();

            //设置RecyclerView中项目的点击监听器
            adapter.setOnItemClickListener(position -> {
                Intent intent = new Intent();
                intent.setClass(context, SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("charsData", resultCharsData);
                bundle.putInt("position", position);
                intent.putExtras(bundle);
                startActivity(intent);
            });

            //“搜索”按钮的点击监听器
            ImageButton button_submit = root.findViewById(R.id.button_search_submit);
            button_submit.setOnClickListener(view ->
                    Toast.makeText(context, R.string.developing, Toast.LENGTH_SHORT).show());
        }
        return root;
    }

    //更新数据
    @SuppressLint("NotifyDataSetChanged")//忽略更新具体数据的要求
    public void updateData() {
        if (resultCharsData != null) {
            resultCharsData.clear();
            getResultCharsData();
            adapter.notifyDataSetChanged();
        }
    }

    //取得搜索结果汉字数据列表
    public void getResultCharsData() {
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("StdChar", null, null,
                    null, null, null, null);
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int stdImgPathIndex = cursor.getColumnIndex("stdImgPath");
            if (cursor.moveToFirst()) {
                do {
                    CharData charData = new CharData();
                    charData.setID(cursor.getInt(idIndex));
                    charData.setName(cursor.getString(nameIndex));
                    charData.setStdImgPath(cursor.getString(stdImgPathIndex));
                    resultCharsData.add(charData);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_search);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        CharCardDecoration decoration = new CharCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new CharCardAdapter(context, resultCharsData);
        recyclerView.setAdapter(adapter);//设置卡片适配器
    }
}
