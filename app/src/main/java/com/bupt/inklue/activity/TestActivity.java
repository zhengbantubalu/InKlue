package com.bupt.inklue.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bupt.evaluate.core.Evaluation;
import com.bupt.evaluate.core.Evaluator;
import com.bupt.inklue.R;

import java.util.ArrayList;
import java.util.Arrays;

//测试评价模块页面
public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<String> c;//存储汉字的列表
    private int i;//计数器

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置当前activity的布局文件
        setContentView(R.layout.activity_test);
        //查找控件并命名
        ImageView img1 = findViewById(R.id.img1);
        ImageView img2 = findViewById(R.id.img2);
        ImageView img3 = findViewById(R.id.img3);
        //设置点击监听器
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        //设置初始图像
        img3.setImageBitmap(BitmapFactory.decodeFile(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/avatar.jpg"));
        //创建汉字列表
        c = new ArrayList<>(Arrays.asList(
                "土", "王", "五", "上", "下", "不", "之", "山", "廿", "四", "日", "石", "六", "天",
                "土", "王", "五", "上", "下", "不", "之", "山", "廿", "四", "日", "石", "六", "天"));
        i = -1;
    }

    //点击监听器
    public void onClick(View view) {
        if (view.getId() == R.id.img1) {
            i = (i + c.size() - 1) % c.size();
        } else if (view.getId() == R.id.img2 || view.getId() == R.id.img3) {
            i = (i + 1) % c.size();
        }
        //取得View
        ImageView img1 = findViewById(R.id.img1);
        ImageView img2 = findViewById(R.id.img2);
        ImageView img3 = findViewById(R.id.img3);
        TextView score = findViewById(R.id.score);
        TextView advice = findViewById(R.id.advice);
        //从外部存储中读取图片
        String name;
        if (i < 14) {
            name = "/" + c.get(i) + "1.jpg";
        } else {
            name = "/" + c.get(i) + "2.jpg";
        }
        Bitmap bitmap1 = BitmapFactory.decodeFile(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                        name);
        Bitmap bitmap2 = BitmapFactory.decodeFile(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                        "/" + c.get(i) + ".jpg");
        //显示读取到的原图像
        img1.setImageBitmap(bitmap1);
        img2.setImageBitmap(bitmap2);
        //调用评价模块
        Evaluation evaluation = Evaluator.evaluate(c.get(i), bitmap1, bitmap2);
        //显示评价模块返回的信息
        img3.setImageBitmap(evaluation.outputBmp);
        score.setText(String.valueOf(evaluation.score));
        advice.setText(evaluation.advice);
    }
}
