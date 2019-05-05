package com.example.etc_manager.utils;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class NetworkUtil extends BroadcastReceiver implements DialogInterface.OnClickListener {
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            Toast.makeText(context, "网络正常", Toast.LENGTH_SHORT).show();
        } else
            showNetworkDialog(context);
    }

    public void showNetworkDialog(Context context) {
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle("是否前往网络设置？")
                .setMessage("未连接网络，请检查网络设置")
                .setPositiveButton("前往设置", this)
                .setNegativeButton("退出程序", this)
                .show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which) {
            case BUTTON_POSITIVE:
                Intent i = new Intent();
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setAction(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                context.startActivity(i);
                break;
            case BUTTON_NEGATIVE:
                System.exit(0);
                break;
        }
    }
}
