package com.example.etc_manager.fragments;

import android.app.Activity;
import android.content.Intent;
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
import com.example.etc_manager.activities.MainActivity;
import com.example.etc_manager.model.User;
import com.example.etc_manager.utils.DateUtil;

import org.litepal.LitePal;

import java.util.List;

public class Fa_setPass extends Fragment implements View.OnClickListener {
    private EditText et_setPass;
    private EditText et_resetPass;
    private EditText et_idNo;

    private Button btn_up;
    private Button btn_apply;

    private String s_pass;
    private String s_repass;
    private String s;
    private String s_idNo;
    private String tel;
    private String btn;
    private int i;


    private FragmentManager manager;
    private Activity activity;

    private static final String ARG_PARAM1 = "tel";
    private static final String ARG_PARAM2 = "btn";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_setpass, container, false);

        manager = getFragmentManager();
        activity = getActivity();

        tel = getParam(1);
        btn = getParam(2);

        et_setPass = v.findViewById(R.id.et_setPass);
        et_resetPass = v.findViewById(R.id.et_resetPass);
        et_idNo = v.findViewById(R.id.et_idNo);
        if (btn.equals("重置")) {
            et_idNo.setVisibility(View.INVISIBLE);
        }


        btn_up = v.findViewById(R.id.btn_up);
        btn_apply = v.findViewById(R.id.btn_applyResetPass);
        btn_apply.setText(btn);

        btn_up.setOnClickListener(this);
        btn_apply.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_up:
                manager.popBackStack();
                break;
            case R.id.btn_applyResetPass:
                setPass();
                break;
        }
    }

    private void setPass() {
        s_pass = String.valueOf(et_setPass.getText());
        s_repass = String.valueOf(et_resetPass.getText());
        s_idNo = String.valueOf(et_idNo.getText());

        if (!TextUtils.isEmpty(s_pass)) {
            if (!TextUtils.isEmpty(s_repass) && s_repass.equals(s_pass)) {
                if (!TextUtils.isEmpty(s_idNo) && s_idNo.length() == 18) {
                    ok();
                    //这里直接登录进去
                    //new Fa_signIn().replaceFrag(manager, new Fa_signIn());
                    startActivity(new Intent(getContext(), MainActivity.class));
                    activity.finish();
                } else
                    Toast.makeText(activity, "请输入18位身份证号", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(activity, "密码不一致", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(activity, "请输入密码", Toast.LENGTH_SHORT).show();
    }

    private void ok() {
        switch (i) {
            case 1:
                User user = new User();
                user.setTel(tel);
                user.setPass(s_pass);
                user.setIdNo(s_idNo);
                user.setGender(Integer.valueOf(s_idNo.substring(16, 17)) % 2 == 0 ? "女" : "男");
                user.setDate(new DateUtil().getDate());
                user.save();
                btn_apply.setClickable(false);
                Toast.makeText(activity, "注册成功", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                List<User> users = LitePal.where("tel like ?", tel).find(User.class);
                user = users.get(0);
                user.setPass(s_pass);
                user.update(1);
                btn_apply.setClickable(false);
                Toast.makeText(activity, "重置成功，新密码为：" + s_pass, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public static Fa_setPass newInstance(String s_tel, String s_btn) {
        Fa_setPass f = new Fa_setPass();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, s_tel);
        args.putString(ARG_PARAM2, s_btn);
        f.setArguments(args);
        return f;
    }

    private String getParam(int t) {
        if (getArguments() != null) {
            switch (t) {
                case 1:
                    s = getArguments().getString(ARG_PARAM1);
                    break;
                case 2:
                    s = getArguments().getString(ARG_PARAM2);
                    if (("确认注册").equals(s)) {
                        i = 1;
                    } else i = 2;
                    break;
            }
        }
        return s;
    }
}
