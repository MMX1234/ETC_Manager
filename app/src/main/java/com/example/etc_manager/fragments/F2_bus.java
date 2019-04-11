package com.example.etc_manager.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.etc_manager.R;
import com.example.etc_manager.utils.Okhttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class F2_bus extends Fragment implements View.OnClickListener {
    private Button btn_info;
    private TextView tv_1_1;
    private TextView tv_1_2;
    private TextView tv_2_1;
    private TextView tv_2_2;

    private String url;
    private String json1;
    private String json2;

    private Integer distance1_1;
    private Integer distance1_2;
    private Integer distance2_1;
    private Integer distance2_2;

    private Boolean isRun = true;

    public F2_bus() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_2_bus, container, false);
        btn_info = v.findViewById(R.id.btn_info);
        btn_info.setOnClickListener(this);

        tv_1_1 = v.findViewById(R.id.tv_1_1);
        tv_1_2 = v.findViewById(R.id.tv_1_2);
        tv_2_1 = v.findViewById(R.id.tv_2_1);
        tv_2_2 = v.findViewById(R.id.tv_2_2);

        url = "http://192.168.0.74:8890/type/jason/action/GetBusstationInfo.do";
        // 这里应该用遍历数组的方式去获取相应站台的信息的，这里简写了
        json1 = "{'BusStationID':1}";
        json2 = "{'BusStationID':2}";

        final int[] i = {0};

        if (isRun) {
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    isRun = false;
                    //getDistance();
                    i[0]++;
                    tv_1_1.setText(String.valueOf(i[0]));
                    handler.postDelayed(this, 500);
                }
            };
            tv_1_1.setText(String.valueOf(i[0]));
            handler.postDelayed(runnable, 500);
        }

        return v;
    }

    public void getDistance() {
        try {
            JSONArray array1 = new JSONArray((new Okhttp().getResult(url, json1)));
            JSONObject object1_1 = array1.getJSONObject(0);
            distance1_1 = object1_1.getInt("Distance");
            JSONObject object1_2 = array1.getJSONObject(1);
            distance1_2 = object1_2.getInt("Distance");

            JSONArray array2 = new JSONArray((new Okhttp().getResult(url, json2)));
            JSONObject object2_1 = array2.getJSONObject(0);
            distance2_1 = object2_1.getInt("Distance");
            JSONObject object2_2 = array2.getJSONObject(1);
            distance2_2 = object2_2.getInt("Distance");

            //人数这里窝没有接口，总载客也没有
            tv_1_1.setText("1号（101人）" + "    " + (distance1_1 * 6) / 2000 + "分钟到达" + "    " + distance1_1 + "米");
            tv_1_2.setText("2号（101人）" + "    " + (distance1_2 * 6) / 2000 + "分钟到达" + "    " + distance1_2 + "米");
            tv_2_1.setText("1号（101人）" + "    " + (distance2_1 * 6) / 2000 + "分钟到达" + "    " + distance2_1 + "米");
            tv_2_2.setText("2号（101人）" + "    " + (distance2_2 * 6) / 2000 + "分钟到达" + "    " + distance2_2 + "米");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        //等下，不是，这个总载客能力是个啥，暂时放这了
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("公交车载客情况统计")
                .setMessage("233");
        builder.create().show();
    }
}
