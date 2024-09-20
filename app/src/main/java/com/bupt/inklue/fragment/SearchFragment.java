package com.bupt.inklue.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bupt.data.api.HanZiApi;
import com.bupt.data.pojo.HanZi;
import com.bupt.inklue.R;
import com.bupt.inklue.activity.SearchActivity;
import com.bupt.inklue.adapter.SearchCardAdapter;
import com.bupt.inklue.decoration.HanZiCardDecoration;
import com.bupt.inklue.util.Constants;
import com.bupt.inklue.util.ResourceHelper;

import java.util.ArrayList;

//“搜索”碎片
public class SearchFragment extends Fragment implements View.OnClickListener {

    private View root;//根视图
    private Context context;//环境
    private SearchCardAdapter adapter;//卡片适配器
    private ArrayList<HanZi> hanZiList;//搜索结果汉字数据列表
    private HanZi filter;//筛选条件
    private EditText editText_search;//搜索框
    private Spinner spinner_style;//书体筛选框
    private Spinner spinner_era;//年代筛选框
    private Spinner spinner_artist;//作者筛选框
    private Spinner spinner_work;//碑帖筛选框
    private SwipeRefreshLayout swipe_refresh_layout;//下拉刷新控件
    private final PracticeFragment practiceFragment;//“练习”碎片

    public SearchFragment(PracticeFragment practiceFragment) {
        this.practiceFragment = practiceFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_search, container, false);
            context = getContext();

            //取得视图
            editText_search = root.findViewById(R.id.editText_search);
            spinner_style = root.findViewById(R.id.spinner_style);
            spinner_era = root.findViewById(R.id.spinner_era);
            spinner_artist = root.findViewById(R.id.spinner_artist);
            spinner_work = root.findViewById(R.id.spinner_work);
            swipe_refresh_layout = root.findViewById(R.id.swipe_refresh_layout);

            //设置刷新控件颜色
            if (context != null) {
                swipe_refresh_layout.setColorSchemeColors(
                        ResourceHelper.getColorInt(context, R.attr.colorTheme));
            }

            //初始化筛选条件
            filter = new HanZi();

            //取得搜索结果汉字数据列表
            hanZiList = HanZiApi.getMatchedHanZiList(context, filter);

            //初始化RecyclerView
            initRecyclerView();

            //设置RecyclerView中项目的点击监听器
            adapter.setOnItemClickListener(this::startSearchActivity);

            //设置下拉刷新监听器
            swipe_refresh_layout.setOnRefreshListener(this::refresh);

            //设置Spinner的选择监听器
            spinner_style.setOnItemSelectedListener(new Listener(spinner_style, context));
            spinner_era.setOnItemSelectedListener(new Listener(spinner_era, context));
            spinner_artist.setOnItemSelectedListener(new Listener(spinner_artist, context));
            spinner_work.setOnItemSelectedListener(new Listener(spinner_work, context));

            //设置按钮的点击监听器
            root.findViewById(R.id.button_search_submit).setOnClickListener(this);
            root.findViewById(R.id.button_filter).setOnClickListener(this);
        }
        return root;
    }

    //Spinner的选择监听器，用于更改Spinner的背景，从而区分是否选择了筛选条件
    static class Listener implements AdapterView.OnItemSelectedListener {

        private final Spinner spinner;
        private final Context context;

        Listener(Spinner spinner, Context context) {
            this.spinner = spinner;
            this.context = context;
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            Drawable drawable;
            if (position == 0) {
                drawable = ContextCompat.getDrawable(context, R.drawable.shape_filter_unselected);
            } else {
                drawable = ContextCompat.getDrawable(context, R.drawable.shape_edittext);
            }
            spinner.setBackground(drawable);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_search_submit) {
            updateFilterCondition();//更新筛选条件
            updateData();//更新数据
        } else if (view.getId() == R.id.button_filter) {
            Toast.makeText(context, R.string.developing, Toast.LENGTH_SHORT).show();
        }
    }

    //子页面关闭的回调
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == Activity.RESULT_FIRST_USER) {
            boolean needUpdate = intent.getBooleanExtra(
                    getString(R.string.update_bundle), false);
            if (needUpdate) {
                practiceFragment.updateData();
            }
        }
    }

    //启动搜索结果查看页面
    private void startSearchActivity(int position) {
        Intent intent = new Intent();
        intent.setClass(context, SearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.han_zi_list_bundle), hanZiList);
        bundle.putInt(getString(R.string.position_bundle), position);
        intent.putExtras(bundle);
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }

    //刷新
    private void refresh() {
        updateFilterCondition();//更新筛选条件
        updateData();//更新数据
        //关闭刷新控件
        new Handler().postDelayed(() ->
                swipe_refresh_layout.setRefreshing(false), Constants.REFRESH_TIME);
    }

    //更新筛选条件
    private void updateFilterCondition() {
        filter.setName(editText_search.getText().toString());
        if (spinner_style.getSelectedItemPosition() != 0) {
            filter.setStyle(spinner_style.getSelectedItem().toString());
        } else {
            filter.setStyle("");
        }
        if (spinner_era.getSelectedItemPosition() != 0) {
            filter.setEra(spinner_era.getSelectedItem().toString());
        } else {
            filter.setEra("");
        }
        if (spinner_artist.getSelectedItemPosition() != 0) {
            filter.setArtist(spinner_artist.getSelectedItem().toString());
        } else {
            filter.setArtist("");
        }
        if (spinner_work.getSelectedItemPosition() != 0) {
            filter.setWork(spinner_work.getSelectedItem().toString());
        } else {
            filter.setWork("");
        }
    }

    //更新数据
    @SuppressLint("NotifyDataSetChanged")//忽略更新具体数据的要求
    public void updateData() {
        if (hanZiList != null) {
            hanZiList.clear();
            hanZiList.addAll(HanZiApi.getMatchedHanZiList(context, filter));
            adapter.notifyDataSetChanged();
        }
    }

    //初始化RecyclerView
    private void initRecyclerView() {
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_search);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(layoutManager);//设置布局管理器
        int spacing = getResources().getDimensionPixelSize(R.dimen.spacing);
        HanZiCardDecoration decoration = new HanZiCardDecoration(spacing);
        recyclerView.addItemDecoration(decoration);//设置间距装饰类
        adapter = new SearchCardAdapter(context, hanZiList);
        recyclerView.setAdapter(adapter);//设置卡片适配器
    }
}
