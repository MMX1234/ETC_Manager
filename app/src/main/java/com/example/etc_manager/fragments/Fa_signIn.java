package com.example.etc_manager.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.etc_manager.R;
import com.example.etc_manager.activities.MainActivity;
import com.example.etc_manager.model.User;

import org.litepal.LitePal;

import java.util.List;

public class Fa_signIn extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private EditText et_tel;
    private EditText et_pass;
    private CheckBox cb_auto;
    private CheckBox cb_remember;
    private String tel = null;
    private String pass = null;

    private List<User> users;
    private SharedPreferences sp;

    private FragmentManager manager;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_a_sign_in, container, false);

        manager = getFragmentManager();
        activity = getActivity();

        et_tel = v.findViewById(R.id.et_tel);
        et_pass = v.findViewById(R.id.et_pass);

        cb_auto = v.findViewById(R.id.cb_auto);
        cb_remember = v.findViewById(R.id.cb_remember);
        cb_auto.setOnCheckedChangeListener(this);
        cb_remember.setOnCheckedChangeListener(this);

        Button btn_signIn = v.findViewById(R.id.btn_signIn);
        Button btn_signUp = v.findViewById(R.id.btn_singUp);
        Button btn_forgetPass = v.findViewById(R.id.btn_forgetPass);
        btn_signIn.setOnClickListener(this);
        btn_signUp.setOnClickListener(this);
        btn_forgetPass.setOnClickListener(this);

        if (activity.getSharedPreferences("signIn", Context.MODE_PRIVATE) != null) {
            sp = activity.getSharedPreferences("signIn", Context.MODE_PRIVATE);
            if (sp.getBoolean("auto", false)) {
                startActivity(new Intent(getContext(), MainActivity.class));
                activity.finish();
            }
            if (sp.getBoolean("remember", false)) {
                et_tel.setText(sp.getString("tel", null));
                et_pass.setText(sp.getString("pass", null));
            }
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signIn:
                onSignInClick();
                break;
            case R.id.btn_singUp:
                replaceFrag(manager, Fa_getCode.newInstance("确认注册"));
                break;
            case R.id.btn_forgetPass:
                replaceFrag(manager, Fa_getCode.newInstance("确认重置"));
                break;
        }
    }

    public void onSignInClick() {
        tel = String.valueOf(et_tel.getText());
        pass = String.valueOf(et_pass.getText());

        if (!TextUtils.isEmpty(tel) && tel.length() == 11) {
            if (!TextUtils.isEmpty(pass)) {
                signIn();
            } else Toast.makeText(getActivity(), "请输入密码", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
    }

    public void signIn() {
        //用输入的手机号与相应的密码比对，一致就成功登录
        users = LitePal.where("tel like ?", tel).find(User.class);
        if (!users.isEmpty()) {
            User user = users.get(0);
            if (user.getPass().equals(pass)) {
                SharedPreferences sp = activity.getSharedPreferences("signIn", Context.MODE_PRIVATE);
                sp.edit().putString("tel", tel).apply();
                if (cb_auto.isChecked()) {
                    sp.edit().putBoolean("auto", true).apply();
                } else sp.edit().putBoolean("auto", false).apply();
                if (cb_remember.isChecked()) {
                    sp.edit().putBoolean("remember", true).putString("pass", pass).apply();
                } else sp.edit().putBoolean("remember", false).apply();
                startActivity(new Intent(getContext(), MainActivity.class));
                activity.finish();
            } else Toast.makeText(getContext(), "手机号码或密码错误", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getContext(), "此手机号未注册", Toast.LENGTH_SHORT).show();
    }


    public void replaceFrag(FragmentManager manager, Fragment fragment) {
        manager.beginTransaction()
                .setCustomAnimations(R.animator.sign_in_in, R.animator.sign_in_out, R.animator.sign_in_in, R.animator.sign_in_out)
                .addToBackStack(null)
                .replace(R.id.frag, fragment)
                .commit();
    }

    public void onKeyDwon(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("提示");
            builder.setMessage("是否退出");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    activity.finish();
                }
            });
            builder.setNegativeButton("取消", null);
            builder.show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (cb_auto.isChecked()) {
            cb_remember.setChecked(true);
        }
    }
}
