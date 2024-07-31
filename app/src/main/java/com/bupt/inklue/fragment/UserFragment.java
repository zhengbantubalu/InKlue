package com.bupt.inklue.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bupt.inklue.R;
import com.bupt.inklue.activity.LoginActivity;
import com.bupt.inklue.activity.RecordActivity;
import com.bupt.inklue.activity.SettingsActivity;
import com.bupt.inklue.adapter.PracticeCardDecoration;
import com.bupt.inklue.adapter.RecordCardAdapter;
import com.bupt.inklue.data.DatabaseHelper;
import com.bupt.inklue.data.PracticeData;
import com.bupt.inklue.util.ResourceDecoder;

import java.util.ArrayList;
import java.util.Collections;

//“我的”碎片
public class UserFragment extends Fragment implements View.OnClickListener {

    private View root;//根视图
    private Context context;//环境
    private RecordCardAdapter adapter;//卡片适配器
    private ArrayList<PracticeData> recordsData;//记录数据列表
    private final PracticeFragment practiceFragment;//“练习”碎片
    private SwipeRefreshLayout swipe_refresh_layout;//下拉刷新控件

    public UserFragment(PracticeFragment practiceFragment) {
        this.practiceFragment = practiceFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_user, container, false);
            context = getContext();

            //取得视图
            swipe_refresh_layout = root.findViewById(R.id.swipe_refresh_layout);

            //设置刷新控件颜色
            if (context != null) {
                swipe_refresh_layout.setColorSchemeColors(
                        ResourceDecoder.getColorInt(context, R.attr.colorTheme));
            }

            //设置用户信息
            setUserinfo();

            //取得记录数据列表
            recordsData = new ArrayList<>();
            getRecordsData();

            //初始化RecyclerView
            initRecyclerView();

            //设置RecyclerView中项目的监听器
            adapter.setOnItemClickListener(this::startRecordActivity);

            //设置下拉刷新监听器
            swipe_refresh_layout.setOnRefreshListener(this::refresh);

            //设置按钮的点击监听器
            root.findViewById(R.id.button_settings).setOnClickListener(this);
            root.findViewById(R.id.user_bar).setOnClickListener(this);
        }
        return root;
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_settings) {
            Intent intent = new Intent();
            intent.setClass(context, SettingsActivity.class);
            startActivityForResult(intent, Activity.RESULT_FIRST_USER);
        } else if (view.getId() == R.id.user_bar) {
            Intent intent = new Intent();
            intent.setClass(context, LoginActivity.class);
            startActivity(intent);
        }
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

    //子页面关闭的回调
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_FIRST_USER) {
            boolean needUpdate = intent.getBooleanExtra("needUpdate", false);
            if (needUpdate) {
                this.updateData();
                practiceFragment.updateData();
            }
        }
    }

    //启动练习记录页面
    private void startRecordActivity(int position) {
        Intent intent = new Intent();
        intent.setClass(context, RecordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("recordData", recordsData.get(position));
        intent.putExtras(bundle);
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }

    //刷新
    private void refresh() {
        updateData();//更新数据
        //关闭刷新控件，延迟500ms
        new Handler().postDelayed(() -> swipe_refresh_layout.setRefreshing(false), 500);
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
            int charIDsIndex = cursor.getColumnIndex("charIDs");
            if (cursor.moveToFirst()) {
                do {
                    PracticeData practiceData = new PracticeData();
                    practiceData.setID(cursor.getLong(idIndex));
                    practiceData.setName(cursor.getString(nameIndex));
                    practiceData.setTime(cursor.getString(timeIndex));
                    practiceData.setCoverImgPath(cursor.getString(coverImgPathIndex));
                    practiceData.setCharIDs(cursor.getString(charIDsIndex));
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
