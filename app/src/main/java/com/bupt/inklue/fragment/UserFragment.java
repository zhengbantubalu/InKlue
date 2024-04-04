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
import com.bupt.inklue.adapter.PracticeCardAdapter;
import com.bupt.inklue.adapter.PracticeCardDecoration;
import com.bupt.inklue.data.CardData;
import com.bupt.inklue.data.CardsData;
import com.bupt.inklue.data.DatabaseHelper;
import com.bupt.inklue.data.DatabaseManager;

//“我的”碎片
public class UserFragment extends Fragment {

    private View root;//根视图
    private Context context;//环境
    private PracticeCardAdapter adapter;//卡片适配器
    private View userCard;//用户卡片视图
    private CardsData recordCardsData;//记录卡片数据

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_user, container, false);
            userCard = inflater.inflate(R.layout.item_user_card, container, false);
            context = getContext();

            //取得记录卡片数据
            recordCardsData = new CardsData();
            getCardsData();

            //初始化RecyclerView
            initRecyclerView();

            //RecyclerView中项目的点击监听器
            adapter.setOnItemClickListener(position -> {
                Intent intent = new Intent();
                intent.setClass(context, RecordActivity.class);
                intent.putExtra("recordCardID", recordCardsData.get(position).getID());
                startActivity(intent);
            });

            //“设置”按钮的点击监听器
            ImageButton button_settings = root.findViewById(R.id.button_settings);
            button_settings.setOnClickListener(view -> {
                DatabaseManager.resetDatabase(context);
                int size = recordCardsData.size();
                recordCardsData.clear();
                adapter.notifyItemRangeRemoved(0, size);
            });
        }
        return root;
    }

    //更新数据
    @SuppressLint("NotifyDataSetChanged")//忽略更新具体数据的要求
    public void updateData() {
        recordCardsData.clear();
        getCardsData();
        adapter.notifyDataSetChanged();
    }

    //取得记录卡片数据
    private void getCardsData() {
        try (DatabaseHelper dbHelper = new DatabaseHelper(context)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("Record", null, null,
                    null, null, null, null);
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int coverImgPathIndex = cursor.getColumnIndex("coverImgPath");
            if (cursor.moveToFirst()) {
                do {
                    CardData cardData = new CardData();
                    cardData.setID(cursor.getLong(idIndex));
                    cardData.setName(cursor.getString(nameIndex));
                    cardData.setStdImgPath(cursor.getString(coverImgPathIndex));
                    recordCardsData.add(cardData);
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
        adapter = new PracticeCardAdapter(context, recordCardsData);
        recyclerView.setAdapter(adapter);//设置卡片适配器
    }

    //设置用户卡片
    private void setUserCard() {
        ImageView user_avatar = userCard.findViewById(R.id.user_avatar);
        Bitmap bitmap = BitmapFactory.decodeFile(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                        "/avatar.jpg");
        user_avatar.setImageBitmap(bitmap);
    }
}
