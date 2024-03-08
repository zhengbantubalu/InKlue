package com.bupt.inklue.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bupt.evaluate.Evaluation;
import com.bupt.inklue.R;

import java.util.ArrayList;
import java.util.Arrays;

//测试评价模块
public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> c;//存储汉字的列表
    int i;//计数器

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置当前activity的布局文件
        setContentView(R.layout.activity_test);
        //查找控件并命名
        ImageView img2 = findViewById(R.id.img2);
        //设置点击监听器
        img2.setOnClickListener(this);
        //设置初始图像
        img2.setImageBitmap(BitmapFactory.decodeFile(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/clickme.jpg"));
        //创建汉字列表
        c = new ArrayList<>(Arrays.asList(
                "土", "王", "五", "上", "下", "不", "之", "山", "廿", "四", "日", "石", "六", "天"));
        i = 0;
    }

    //点击监听器
    public void onClick(View view) {
        if (view.getId() == R.id.img2) {
            //取得View
            ImageView img1 = findViewById(R.id.img1);
            ImageView img2 = findViewById(R.id.img2);
            TextView score = findViewById(R.id.score);
            TextView advice = findViewById(R.id.advice);
            //从外部存储中读取图片
            Bitmap bitmap = BitmapFactory.decodeFile(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                            "/" + c.get(i) + ".jpg");
            //显示读取到的原图像
            img1.setImageBitmap(bitmap);
            //调用评价模块
            Evaluation evaluation = new Evaluation(c.get(i), bitmap);
            //显示评价模块返回的信息
            img2.setImageBitmap(evaluation.outputImg);
            score.setText(String.valueOf(evaluation.score));
            advice.setText(evaluation.advice.toString());
            //循环计数器
            i = (i + 1) % c.size();
        }
    }
}
