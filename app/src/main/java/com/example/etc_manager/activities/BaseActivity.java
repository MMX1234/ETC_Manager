package com.example.etc_manager.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.etc_manager.R;

public class BaseActivity extends AppCompatActivity {

    private Context mContext;
    private Bitmap mNav;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        // 隐藏标题栏
        //if (getSupportActionBar() != null)
        //getSupportActionBar().hide();

        // 沉浸效果
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }*/

        // 默认屏幕方向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mContext = this;

        // 执行初始化方法
        init();
    }

    public void init() {
        ImageView img_nav = findViewById(R.id.img_nav);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        AlphaAnimation anim = new AlphaAnimation(1.0f, 1.0f);
        anim.setDuration(1000);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                openMainUI();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        img_nav.setAnimation(anim);
        anim.start();
    }

    public void openMainUI() {

        // 判断是否登录过
        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //if (preferences.getString("isLogined", null) != null) {
        startActivity(new Intent().setClass(this, MainActivity.class));
        //} else {
        //  Toast.makeText(this, "首次运行请登录", Toast.LENGTH_SHORT).show();
        //  startActivity(new Intent().setClass(this, LoginActivity.class));
        // }
        finish();

    }
}
