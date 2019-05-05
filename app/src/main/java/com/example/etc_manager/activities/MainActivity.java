package com.example.etc_manager.activities;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.etc_manager.R;
import com.example.etc_manager.fragments.F0_account;
import com.example.etc_manager.fragments.F1_bill;
import com.example.etc_manager.fragments.F2_bus;
import com.example.etc_manager.fragments.F3_user;
import com.example.etc_manager.fragments.F4_life;
import com.example.etc_manager.fragments.F5_env;
import com.example.etc_manager.fragments.F6_light;
import com.example.etc_manager.fragments.F7_road;
import com.example.etc_manager.fragments.Fa_signIn;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment[] fragments;
    private FragmentManager fm = getSupportFragmentManager();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 标题栏
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showLayout();
    }

    private void showLayout() {
        // 抽屉开关
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // 导航视图监听
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //创建frag对象
        fragments = new Fragment[]{
                new F0_account(),
                new F1_bill(),
                new F2_bus(),
                new F3_user(),
                new F4_life(),
                new F5_env(),
                new F6_light(),
                new F7_road()
        };

        toolbar.setTitle("我的账户");
        fm.beginTransaction().replace(R.id.frag, fragments[0]).commit();
    }

    // 抽屉返回事件处理
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // 抽屉菜单选项创建
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // 选项选择判断
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 导航选项事件处理
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {
            case R.id.nav_0_account:
                toolbar.setTitle(item.getTitle());
                fm.beginTransaction().replace(R.id.frag, fragments[0]).commit();
                break;
            case R.id.nav_1_bill:
                toolbar.setTitle(item.getTitle());
                fm.beginTransaction().replace(R.id.frag, fragments[1]).commit();
                break;
            case R.id.nav_2_bus:
                toolbar.setTitle(item.getTitle());
                fm.beginTransaction().replace(R.id.frag, fragments[2]).commit();
                break;
            case R.id.nav_3_user:
                toolbar.setTitle(item.getTitle());
                fm.beginTransaction().replace(R.id.frag, fragments[3]).commit();
                break;
            case R.id.nav_4_life:
                toolbar.setTitle(item.getTitle());
                fm.beginTransaction().replace(R.id.frag, fragments[4]).commit();
                break;
            case R.id.nav_5_env:
                toolbar.setTitle(item.getTitle());
                fm.beginTransaction().replace(R.id.frag, fragments[5]).commit();
                break;
            case R.id.nav_6_light:
                toolbar.setTitle(item.getTitle());
                fm.beginTransaction().replace(R.id.frag, fragments[6]).commit();
                break;
            case R.id.nav_7_road:
                toolbar.setTitle(item.getTitle());
                fm.beginTransaction().replace(R.id.frag, fragments[7]).commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
