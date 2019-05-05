package com.example.etc_manager.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.etc_manager.R;
import com.example.etc_manager.activities.MainActivity;
import com.example.etc_manager.model.User;

import static android.content.ContentValues.TAG;

public class Fa_signUp extends Fragment implements View.OnClickListener {
    private EditText et_uid;
    private EditText et_pass;
    private EditText et_repass;
    private EditText et_idNo;
    private EditText et_name;
    private EditText et_tel;
    private Button btn_OK;
    private Button btn_cancel;

    private String uid;
    private String pass;
    private String repass;
    private String idNo;
    private String name;
    private String tel;

    private User user = new User();
    private FragmentManager manager;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_a_sign_up, container, false);

        manager = getFragmentManager();
        activity = getActivity();

        et_uid = v.findViewById(R.id.et_uid);
        et_pass = v.findViewById(R.id.et_pass);
        et_repass = v.findViewById(R.id.et_repass);
        et_idNo = v.findViewById(R.id.et_idNo);
        et_name = v.findViewById(R.id.et_name);
        et_tel = v.findViewById(R.id.et_tel);
        btn_OK = v.findViewById(R.id.btn_OK);
        btn_cancel = v.findViewById(R.id.btn_cancel);
        btn_OK.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_OK:
                onBtn_OKClick();
                break;
            case R.id.btn_cancel:
                manager.popBackStack();
                break;
        }
    }

    public void onBtn_OKClick() {
        uid = String.valueOf(et_uid.getText());
        pass = String.valueOf(et_pass.getText());
        repass = String.valueOf(et_repass.getText());
        idNo = String.valueOf(et_idNo.getText());
        name = String.valueOf(et_name.getText());
        tel = String.valueOf(et_tel.getText());

        if (!TextUtils.isEmpty(uid)) {
            if (!TextUtils.isEmpty(pass)) {
                if (repass.equals(pass)) {
                    if (!TextUtils.isEmpty(idNo)) {
                        if (!TextUtils.isEmpty(name)) {
                            if (!TextUtils.isEmpty(tel)) {
                                signUp();
                            } else
                                Toast.makeText(getContext(), "请输入联系电话", Toast.LENGTH_SHORT).show();
                        } else Toast.makeText(getContext(), "请输入身份证姓名", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(getContext(), "请输入身份证号", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getContext(), "密码不一致，请再次输入密码", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
    }

    public void signUp() {
        user.setUid(uid);
        user.setPass(pass);
        user.setUid(idNo);
        user.setUid(name);
        user.setUid(tel);
        user.save();
        Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "signUp: " + uid + "--" + pass + "--" + repass + "--" + idNo + "--" + name + "--" + tel);
        startActivity(new Intent(getContext(), MainActivity.class));
        activity.finish();
    }
}
