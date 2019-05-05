package com.example.etc_manager.activities;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.etc_manager.R;
import com.example.etc_manager.fragments.Fa_signIn;
import com.example.etc_manager.utils.NetworkUtil;

import org.litepal.LitePal;

public class SignActivity extends AppCompatActivity {
    private FragmentManager fm = getSupportFragmentManager();
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LitePal.initialize(this);
        setContentView(R.layout.activity_sign);

        // 标题栏
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("用户登录");
        fm.beginTransaction()
                .replace(R.id.frag, new Fa_signIn())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(new NetworkUtil(),new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }
}
