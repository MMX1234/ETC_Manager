package com.example.etc_manager.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.etc_manager.R;

import java.util.Random;

public class Fa_resetPass_getCode extends Fragment implements View.OnClickListener {
    private EditText et_phone;
    private String s_phone;
    private EditText et_code;
    private String s_code;
    private Button btn_getCode;
    private Button btn_next;
    private Button btn_back;
    private String sCode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_resetpass_getcode, container, false);
        et_phone = v.findViewById(R.id.et_phone);
        et_code = v.findViewById(R.id.et_code);
        btn_getCode = v.findViewById(R.id.btn_getCode);
        btn_next = v.findViewById(R.id.btn_next);
        btn_back = v.findViewById(R.id.btn_back);

        btn_getCode.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        s_phone = String.valueOf(et_phone.getText());
        s_code = String.valueOf(et_code.getText());
        switch (v.getId()) {
            case R.id.btn_getCode:
                phoneIsEmpty();
                break;
            case R.id.btn_next:
                if (TextUtils.isEmpty(s_code)) {
                    Toast.makeText(getContext(), "请输入验证码: " + s_code, Toast.LENGTH_SHORT).show();
                } else {
                    if (s_code.equals(sCode)) {
                        new Fa_signIn().replaceFrag(new Fa_resetPass());
                    } else {
                        Toast.makeText(getContext(), "验证码有误，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_back:
                getFragmentManager().popBackStack();
                break;
        }
    }

    private void getCode() {
        sCode = "";
        //这里采用简单的随机数
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 6; i++) {
            sCode = sCode + random.nextInt(10);
        }
        Toast.makeText(getContext(), "本次验证码为：" + sCode, Toast.LENGTH_SHORT).show();
    }

    private void phoneIsEmpty() {
        if (TextUtils.isEmpty(s_phone)) {
            Toast.makeText(getContext(), "请输入手机号: " + s_phone, Toast.LENGTH_SHORT).show();
        } else getCode();
    }
}
