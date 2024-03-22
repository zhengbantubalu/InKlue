package com.bupt.inklue.ui;

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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
                getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/avatar.jpg"));
        //创建汉字列表
        c = new ArrayList<>(Arrays.asList(
                "土", "王", "五", "上", "下", "不", "之", "山", "廿", "四", "日", "石", "六", "天",
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
            String name;
            if (i < 14) {
                name = "/" + c.get(i) + ".jpg";
            } else {
                name = "/" + c.get(i) + "1.jpg";
            }
            Bitmap bitmap1 = BitmapFactory.decodeFile(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                            name);
            Bitmap bitmap2 = BitmapFactory.decodeFile(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES) +
                            "/" + c.get(i) + ".jpg");
            //显示读取到的原图像
            img1.setImageBitmap(bitmap2);
            //调用评价模块
            Evaluation evaluation = Evaluator.evaluate(c.get(i), bitmap1, bitmap2);
            //显示评价模块返回的信息
            img2.setImageBitmap(evaluation.outputBmp);
//            saveBitmap(evaluation.outputImg, c.get(i) + "1.jpg");
            score.setText(String.valueOf(evaluation.score));
            advice.setText(evaluation.advice.toString());
            //循环计数器
            i = (i + 1) % c.size();
        }
    }

    //存储Bitmap方法，图片被以jpg格式存储在app的私有外部存储中的图片目录中，不需要申请权限，其他app无法访问
    public void saveBitmap(Bitmap bitmap, String fileName) {
        File image = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName);
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(Files.newOutputStream(image.toPath()));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException ignored) {
        }
    }
}
