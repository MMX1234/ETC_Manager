package com.example.etc_manager.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.etc_manager.R;
import com.example.etc_manager.activities.MainActivity;
import com.example.etc_manager.activities.SignActivity;
import com.example.etc_manager.utils.MyDatabaseHelper;

public class Fa_signIn extends Fragment implements View.OnClickListener {
    private EditText et_uid;
    private EditText et_pass;
    private String name = null;
    private String pass = null;

    private MyDatabaseHelper helper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_a_sign_in, container, false);
        et_uid = v.findViewById(R.id.et_uid);
        et_pass = v.findViewById(R.id.et_pass);

        CheckBox cb_auto = v.findViewById(R.id.cb_auto);
        CheckBox cb_remember = v.findViewById(R.id.cb_remember);
        Button btn_signIn = v.findViewById(R.id.btn_signIn);
        Button btn_signUp = v.findViewById(R.id.btn_singUp);
        btn_signIn.setOnClickListener(this);
        btn_signUp.setOnClickListener(this);

        helper = new MyDatabaseHelper(getContext(), "ETC.db", null, 1);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signIn:
                name = String.valueOf(et_uid.getText());
                pass = String.valueOf(et_pass.getText());
                if (!TextUtils.isEmpty(name)) {
                    if (!TextUtils.isEmpty(pass)) {
                        //signIn();
                        Toast.makeText(getContext(), name + pass, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "请输入密码", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_singUp:
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.animator.sign_in_in, R.animator.sign_in_out)
                        .addToBackStack(null)
                        .replace(R.id.frag, new Fa_signUp())
                        .commit();
                break;
        }
    }

    public void signIn() {

        SQLiteDatabase db = helper.getWritableDatabase();
        //用输入的用户名与相应的密码比对，一致就成功登录
        Cursor cursor = db.query("User", null, null, null, null, null, null);
        cursor.close();
        if (cursor.getString(cursor.getColumnIndex("uid")) != null &&
                cursor.getString(cursor.getColumnIndex("pass")).equals(pass)) {
            getActivity().onBackPressed();
        } else {
            Toast.makeText(getContext(), "不存在此用户名", Toast.LENGTH_SHORT).show();
        }
    }
}
