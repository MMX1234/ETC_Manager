package com.example.etc_manager.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.etc_manager.R;
import com.example.etc_manager.model.User;

import org.litepal.LitePal;

import java.util.List;
import java.util.Random;

public class Fa_getCode extends Fragment implements View.OnClickListener {
    private EditText et_tel;
    private String tel;
    private EditText et_code;
    private String s_code;
    private Button btn_getCode;
    private Button btn_next;
    private Button btn_back;
    private String sCode;
    private String s;
    private int i;

    private FragmentManager manager;
    private Activity activity;

    private static final String ARG_PARAM = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_getcode, container, false);

        manager = getFragmentManager();
        activity = getActivity();

        et_tel = v.findViewById(R.id.et_tel);
        et_code = v.findViewById(R.id.et_code);
        btn_getCode = v.findViewById(R.id.btn_getCode);
        btn_next = v.findViewById(R.id.btn_next);
        btn_back = v.findViewById(R.id.btn_back);

        btn_getCode.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        getParam();
        return v;
    }

    @Override
    public void onClick(View v) {
        tel = String.valueOf(et_tel.getText());
        s_code = String.valueOf(et_code.getText());
        switch (v.getId()) {
            case R.id.btn_getCode:
                phoneIsEmpty();
                break;
            case R.id.btn_next:
                if (!TextUtils.isEmpty(s_code) && s_code.equals(sCode)) {
                    //需判断getParam是否为空
                    new Fa_signIn().replaceFrag(manager, Fa_setPass.newInstance(tel, getParam()));
                } else {
                    Toast.makeText(activity, "验证码有误，请重新输入", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_back:
                manager.popBackStack();
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
        Toast.makeText(activity, "本次验证码为：" + sCode, Toast.LENGTH_LONG).show();
    }

    private void phoneIsEmpty() {
        if (!TextUtils.isEmpty(tel) && tel.length() == 11) {
            existTel();
        } else Toast.makeText(activity, "请输入11位手机号", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        et_code.setText("");
        sCode = null;
    }

    public static Fa_getCode newInstance(String s1) {
        Fa_getCode f = new Fa_getCode();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, s1);
        f.setArguments(args);
        return f;
    }

    private String getParam() {
        if (getArguments() != null) {
            s = getArguments().getString(ARG_PARAM);
            if (("确认注册").equals(s)) {
                i = 1;
            } else i = 2;
        }
        return s;
    }

    private void existTel() {
        List<User> users = LitePal.where("tel like ?", tel).find(User.class);
        switch (i) {
            case 1:
                if (users.isEmpty()) {
                    getCode();
                } else Toast.makeText(activity, "此号码已被注册", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                if (!users.isEmpty()) {
                    getCode();
                } else Toast.makeText(activity, "此号码未注册", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
