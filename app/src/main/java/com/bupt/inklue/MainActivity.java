package com.bupt.inklue;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置当前activity的布局文件
        setContentView(R.layout.activity_main);
        //查找控件并命名
        ImageView img2 = findViewById(R.id.img2);
        //将imageview的资源直接设置为jpg图片
        img2.setImageResource(R.drawable.test);
    }

}
