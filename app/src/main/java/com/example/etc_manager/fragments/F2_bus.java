package com.example.etc_manager.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.etc_manager.R;

public class F2_bus extends Fragment {
    private Button btn_info;
    private TextView tv_1_1;
    private TextView tv_1_2;
    private TextView tv_2_1;
    private TextView tv_2_2;

    private String url;
    private String json1;
    private String json2;

    public F2_bus() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_2_bus, container, false);
        btn_info = view.findViewById(R.id.btn_info);
        tv_1_1 = view.findViewById(R.id.tv_1_1);
        tv_1_2 = view.findViewById(R.id.tv_1_2);
        tv_2_1 = view.findViewById(R.id.tv_2_1);
        tv_2_2 = view.findViewById(R.id.tv_2_2);


        return view;
    }

    public void getDistance() {
        url = "http://192.168.0.74:8890/type/jason/action/GetBusstationInfo.do";
        // 这里应该用遍历数组的方式去获取相应站台的信息的，这里简写了
        json1 = "{'BusStationID':1}";
        json2 = "{'BusStationID':2}";
    }

}
