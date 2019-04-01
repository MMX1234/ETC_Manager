package com.example.etc_manager.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.etc_manager.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_name;
    private EditText et_pass;
    private CheckBox cb_auto;
    private CheckBox cb_remember;
    private Button btn_login;
    private Button btn_reg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_name = findViewById(R.id.et_name);
        et_pass = findViewById(R.id.et_pass);
        cb_auto = findViewById(R.id.cb_auto);
        cb_remember = findViewById(R.id.cb_remember);
        btn_login = findViewById(R.id.btn_login);
        btn_reg = findViewById(R.id.btn_reg);
        btn_login.setOnClickListener(this);
        btn_reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if ("admin".equals(et_name.getText().toString()) &&
                        "12345".equals(et_pass.getText().toString())) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit().putString("isLogined", "1");
                    editor.apply();
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent().setClass(this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_reg:
                if (et_name.getText() != null && et_pass.getText() != null) {
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "请输入用户名和密码注册", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cb_auto:
                break;
            case R.id.cb_remember:
                break;
        }
    }
}
