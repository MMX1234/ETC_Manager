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
    private EditText et_uid;
    private EditText et_pass;
    private CheckBox cb_auto;
    private CheckBox cb_remember;
    private String uid = null;
    private String pass = null;

    private FragmentManager manager;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_a_sign_in, container, false);

        manager = getFragmentManager();
        activity=getActivity();

        et_uid = v.findViewById(R.id.et_uid);
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

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signIn:
                onSignInClick();
                break;
            case R.id.btn_singUp:
                replaceFrag(manager,new Fa_signUp());
                break;
            case R.id.btn_forgetPass:
                replaceFrag(manager,new Fa_resetPass_getCode());
                break;
        }
    }

    public void onSignInClick() {
        uid = String.valueOf(et_uid.getText());
        pass = String.valueOf(et_pass.getText());

        if (!TextUtils.isEmpty(uid)) {
            if (!TextUtils.isEmpty(pass)) {
                Toast.makeText(getContext(), uid + pass, Toast.LENGTH_SHORT).show();
                signIn();
            } else Toast.makeText(getActivity(), "请输入密码", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
    }

    public void signIn() {
        //用输入的用户名与相应的密码比对，一致就成功登录
        List<User> users = LitePal.where("uid like ?", uid).find(User.class);
        if (!users.isEmpty()) {
            User user = users.get(0);
            if (user.getUid() != null) {
                if (user.getPass().equals(pass)) {
                    startActivity(new Intent(getContext(), MainActivity.class));
                    activity.finish();
                } else Toast.makeText(getContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(getContext(), "不存在此用户名", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getContext(), "不存在此用户名", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_auto:
                if (cb_auto.isChecked()) {
                    //SharedPreferences sp = getActivity().getSharedPreferences("", Context.MODE_PRIVATE);
                    Toast.makeText(getContext(), "自动登录被选中", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getContext(), "自动登录未选中", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cb_remember:
                break;
        }
    }

    public void replaceFrag(FragmentManager manager,Fragment fragment) {
        manager.beginTransaction()
                .setCustomAnimations(R.animator.sign_in_in, R.animator.sign_in_out, R.animator.sign_in_in, R.animator.sign_in_out)
                .addToBackStack(null)
                .replace(R.id.frag, fragment)
                .commit();
    }
}
