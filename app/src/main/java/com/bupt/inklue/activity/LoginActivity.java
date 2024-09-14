package com.bupt.inklue.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.bupt.inklue.R;

//登录页面
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edittext_account;//账号
    private EditText edittext_password;//密码

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //取得视图
        edittext_account = findViewById(R.id.edittext_account);
        edittext_password = findViewById(R.id.edittext_password);

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_login).setOnClickListener(this);
        findViewById(R.id.button_register).setOnClickListener(this);
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_login) {
        } else if (view.getId() == R.id.button_register) {
        }
    }
}
