package com.bupt.inklue.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bupt.inklue.R;
import com.bupt.inklue.activity.RecordActivity;
import com.bupt.inklue.activity.SettingsActivity;
import com.bupt.inklue.adapter.PracticeCardDecoration;
import com.bupt.inklue.adapter.RecordCardAdapter;
import com.bupt.inklue.data.DatabaseHelper;
import com.bupt.inklue.data.PracticeData;

import java.util.ArrayList;
import java.util.Collections;

//“我的”碎片
public class UserFragment extends Fragment {

    private View root;//根视图
    private Context context;//环境
    private RecordCardAdapter adapter;//卡片适配器
    private ArrayList<PracticeData> recordsData;//记录数据列表

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_user, container, false);
            context = getContext();

            //设置用户信息
            setUserinfo();

            //取得记录数据列表
            recordsData = new ArrayList<>();
            getRecordsData();

            //初始化RecyclerView
            initRecyclerView();

            //RecyclerView中项目的点击监听器
            adapter.setOnItemClickListener(position -> {
                Intent intent = new Intent();
                intent.setClass(context, RecordActivity.class);
                intent.putExtra("recordID", recordsData.get(position).getID());
                startActivity(intent);
            });

            //“设置”按钮的点击监听器
            ImageButton button_settings = root.findViewById(R.id.button_settings);
            button_settings.setOnClickListener(view -> {
                Intent intent = new Intent();
                intent.setClass(context, SettingsActivity.class);
                startActivity(intent);
            });
        }
        return root;
    }

    //更新数据
    @SuppressLint("NotifyDataSetChanged")//忽略更新具体数据的要求
    public void updateData() {
        if (recordsData != null) {
            recordsData.clear();
            getRecordsData();
            adapter.notifyDataSetChanged();
        }
    }

    //设置用户信息
    private void setUserinfo() {
        ImageView user_avatar = root.findViewById(R.id.user_avatar);
        Bitmap bitmap = BitmapFactory.decodeFile(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                        "/avatar.jpg");
        user_avatar.setImageBitmap(bitmap);
    }

    //取得记录数据列表
    private void getRecordsData() {
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("Record", null, null,
                    null, null, null, null);
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int timeIndex = cursor.getColumnIndex("time");
            int coverImgPathIndex = cursor.getColumnIndex("coverImgPath");
            if (cursor.moveToFirst()) {
                do {
                    PracticeData practiceData = new PracticeData();
                    practiceData.setID(cursor.getLong(idIndex));
                    practiceData.setName(cursor.getString(nameIndex));
                    practiceData.setTime(cursor.getString(timeIndex));
                    practiceData.setCoverImgPath(cursor.getString(coverImgPathIndex));
                    recordsData.add(practiceData);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        Collections.reverse(recordsData);//反转记录数据列表
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_practice);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        PracticeCardDecoration decoration = new PracticeCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new RecordCardAdapter(context, recordsData);
        recyclerView.setAdapter(adapter);//设置卡片适配器
    }
}
