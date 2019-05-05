package com.example.etc_manager.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.etc_manager.R;

public class Fa_resetPass extends Fragment implements View.OnClickListener {
    private EditText et_resetPass;
    private EditText et_reresetPass;
    private Button btn_up;
    private Button btn_rePass;

    private FragmentManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_resetpass, container, false);

        manager=getFragmentManager();

        et_resetPass = v.findViewById(R.id.et_resetPass);
        et_reresetPass = v.findViewById(R.id.et_reresetPass);
        btn_up = v.findViewById(R.id.btn_up);
        btn_rePass = v.findViewById(R.id.btn_applyResetPass);
        btn_up.setOnClickListener(this);
        btn_rePass.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_up:
                manager.popBackStack();
                break;
            case R.id.btn_applyResetPass:
                break;
        }
    }
}
