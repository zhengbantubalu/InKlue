package com.bupt.inklue;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bupt.evaluate.Evaluation;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置当前activity的布局文件
        setContentView(R.layout.activity_main);
        //查找控件并命名
        ImageView img1 = findViewById(R.id.img1);
        ImageView img2 = findViewById(R.id.img2);
        TextView score = findViewById(R.id.score);
        TextView advice = findViewById(R.id.advice);
        //将imageview的资源直接设置为jpg图片
        img1.setImageResource(R.drawable.test);
        //将jpg图片转换为bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        //调用评价模块
        Evaluation evaluation = new Evaluation("土", bitmap);
        //显示评价模块返回的信息
        img2.setImageBitmap(evaluation.outputImg);
        score.setText(String.valueOf(evaluation.score));
        advice.setText(evaluation.advice.toString());
    }

}
