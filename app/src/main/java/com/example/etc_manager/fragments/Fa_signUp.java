package com.example.etc_manager.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.etc_manager.R;

public class Fa_signUp extends Fragment implements View.OnClickListener {
    private Button btn_cancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_a_sign_up, container, false);
        btn_cancel = v.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.sign_in_in, R.animator.sign_in_out)
                .addToBackStack(null)
                .replace(R.id.frag, new Fa_signIn())
                .commit();
    }
}
