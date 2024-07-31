package com.bupt.inklue.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bupt.inklue.R;
import com.bupt.inklue.data.SqliteOpenHelper;
import com.bupt.inklue.data.UserData;

//登录页面
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edittext_account;//账号
    private EditText edittext_password;//密码
    private SqliteOpenHelper sqliteOpenHelper;//数据库帮助类

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //取得数据库帮助类
        sqliteOpenHelper = new SqliteOpenHelper(this);

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
            String n = edittext_account.getText().toString();
            String p = edittext_password.getText().toString();
            boolean load = sqliteOpenHelper.load(n, p);
            if (load) {
                Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.login_error, Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.button_register) {
            String N = edittext_account.getText().toString();
            String P = edittext_password.getText().toString();
            UserData u = new UserData(N, P);
            long sign = sqliteOpenHelper.register(u);
            if (sign != -1) {
                Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.register_error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
