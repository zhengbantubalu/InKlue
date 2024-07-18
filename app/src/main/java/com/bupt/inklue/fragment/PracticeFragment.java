package com.bupt.inklue.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bupt.inklue.R;
import com.bupt.inklue.activity.PracticeActivity;
import com.bupt.inklue.adapter.PracticeCardAdapter;
import com.bupt.inklue.adapter.PracticeCardDecoration;
import com.bupt.inklue.data.DatabaseHelper;
import com.bupt.inklue.data.PracticeData;
import com.bupt.inklue.data.PracticeDataManager;
import com.bupt.inklue.util.ResourceDecoder;

import java.util.ArrayList;
import java.util.Collections;

//“练习”碎片
public class PracticeFragment extends Fragment implements View.OnClickListener {

    private View root;//根视图
    private Context context;//环境
    private PracticeCardAdapter adapter;//卡片适配器
    private ArrayList<PracticeData> practicesData;//练习数据列表
    private SwipeRefreshLayout swipe_refresh_layout;//下拉刷新控件

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_practice, container, false);
            context = getContext();

            //取得视图
            swipe_refresh_layout = root.findViewById(R.id.swipe_refresh_layout);

            //设置刷新控件颜色
            if (context != null) {
                swipe_refresh_layout.setColorSchemeColors(
                        ResourceDecoder.getColorInt(context, R.attr.colorTheme));
            }

            //取得练习数据列表
            practicesData = new ArrayList<>();
            getPracticesData();

            //初始化RecyclerView
            initRecyclerView();

            //设置RecyclerView中项目的点击监听器
            adapter.setOnItemClickListener(this::startPracticeActivity);

            //设置下拉刷新监听器
            swipe_refresh_layout.setOnRefreshListener(this::refresh);

            //设置按钮的点击监听器
            root.findViewById(R.id.button_add).setOnClickListener(this);
        }
        return root;
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_add) {
            createPractice();
        }
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

    //子页面关闭的回调
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_FIRST_USER) {
            boolean needUpdate = intent.getBooleanExtra("needUpdate", false);
            if (needUpdate) {
                updateData();
            }
        }
    }

    //启动练习详情页面
    private void startPracticeActivity(int position) {
        Intent intent = new Intent();
        intent.setClass(context, PracticeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("practiceData", practicesData.get(position));
        intent.putExtras(bundle);
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }

    //刷新
    private void refresh() {
        updateData();//更新数据
        //关闭刷新控件，延迟500ms
        new Handler().postDelayed(() -> swipe_refresh_layout.setRefreshing(false), 500);
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
        Collections.reverse(practicesData);//反转练习数据列表
    }

    //创建练习
    private void createPractice() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.create);
        final EditText input = new EditText(context);
        builder.setView(input);
        builder.setMessage(R.string.input_name_hint);
        builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
            String name = input.getText().toString();
            PracticeDataManager.createPractice(context, name);//在数据库中创建练习
            updateData();//更新数据
        });
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
