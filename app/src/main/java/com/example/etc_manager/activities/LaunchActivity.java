package com.example.etc_manager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.etc_manager.R;

import java.io.File;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        //sleep();
        startActivity();

    }

    private void sleep() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity();
            }
        };
        t.start();
    }

    private void startActivity() {
        if (new File(Environment.getDataDirectory().getPath() + "database/ETC.db").exists()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else {
            startActivity(new Intent(getApplicationContext(), SignActivity.class));
        }
        finish();
    }
}
