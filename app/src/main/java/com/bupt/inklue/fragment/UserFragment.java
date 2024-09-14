package com.bupt.inklue.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bupt.inklue.R;
import com.bupt.inklue.activity.LoginActivity;
import com.bupt.inklue.activity.RecordActivity;
import com.bupt.inklue.activity.SettingsActivity;
import com.bupt.inklue.adapter.RecordCardAdapter;
import com.bupt.inklue.data.api.HanZiLogApi;
import com.bupt.inklue.data.api.PracticeLogApi;
import com.bupt.inklue.data.pojo.Practice;
import com.bupt.inklue.decoration.PracticeCardDecoration;
import com.bupt.inklue.util.Constants;
import com.bupt.inklue.util.ResourceHelper;

import java.util.ArrayList;
import java.util.Objects;

//“我的”碎片
public class UserFragment extends Fragment implements View.OnClickListener {

    private View root;//根视图
    private Context context;//环境
    private RecordCardAdapter adapter;//卡片适配器
    private ArrayList<Practice> practiceLogList;//记录数据列表
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
                        ResourceHelper.getColorInt(context, R.attr.colorTheme));
            }

            //设置用户信息
            setUserinfo();

            //取得记录数据列表
            practiceLogList = PracticeLogApi.getPracticeLogList(context);

            //初始化RecyclerView
            initRecyclerView();

            //设置RecyclerView中项目的点击监听器和长按监听器
            adapter.setOnItemClickListener(this::startRecordActivity);
            adapter.setOnItemLongClickListener(this::openMenu);

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
        if (practiceLogList != null) {
            practiceLogList.clear();
            practiceLogList.addAll(PracticeLogApi.getPracticeLogList(context));
            adapter.notifyDataSetChanged();
        }
    }

    //子页面关闭的回调
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_FIRST_USER) {
            boolean needUpdate = intent.getBooleanExtra(
                    getString(R.string.update_bundle), false);
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
        bundle.putSerializable(getString(R.string.practice_bundle), practiceLogList.get(position));
        intent.putExtras(bundle);
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }

    //打开菜单
    private void openMenu(int position) {
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_practice);
        View view = Objects.requireNonNull(recyclerView.getLayoutManager()).findViewByPosition(position);
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_record, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_item_delete) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.delete);
                builder.setMessage(R.string.delete_practice_warning);
                builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
                    Practice practice = practiceLogList.get(position);
                    practice.hanZiList = HanZiLogApi.getPracticeLogHanZiList(context, practice);
                    PracticeLogApi.deletePracticeLog(context, practice);
                    updateData();
                });
                builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    //刷新
    private void refresh() {
        updateData();//更新数据
        //关闭刷新控件
        new Handler().postDelayed(() ->
                swipe_refresh_layout.setRefreshing(false), Constants.REFRESH_TIME);
    }

    //设置用户信息
    private void setUserinfo() {
        ImageView user_avatar = root.findViewById(R.id.user_avatar);
        Bitmap bitmap = BitmapFactory.decodeFile(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                        "/avatar.jpg");
        user_avatar.setImageBitmap(bitmap);
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_practice);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        PracticeCardDecoration decoration = new PracticeCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new RecordCardAdapter(context, practiceLogList);
        recyclerView.setAdapter(adapter);//设置卡片适配器
    }
}
