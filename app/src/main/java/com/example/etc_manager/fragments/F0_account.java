package com.example.etc_manager.fragments;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.etc_manager.R;
import com.example.etc_manager.utils.MyDatabaseHelper;
import com.example.etc_manager.utils.Okhttp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class F0_account extends Fragment implements View.OnClickListener {

    private TextView tv_balance;
    private Spinner sp_carno;
    private EditText et_recharge;
    private Button btn_query;
    private Button btn_recharge;

    private String url;
    private String json;

    private MyDatabaseHelper helper;

    public F0_account() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_0_acoount, container, false);
        tv_balance = v.findViewById(R.id.tv_balance);
        sp_carno = v.findViewById(R.id.sp_carno);
        et_recharge = v.findViewById(R.id.et_recharge);
        btn_query = v.findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);
        btn_recharge = v.findViewById(R.id.btn_recharge);

        String[] spinnerItems = {"1", "2", "3"};
        ArrayAdapter<String> carAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                spinnerItems
        );
        sp_carno.setAdapter(carAdapter);
        url = "http://192.168.0.74:8890/type/jason/action/GetCarAccountBalance.do";
        json = "{'CarId':" + sp_carno.getSelectedItemPosition() + "}";
        tv_balance.setText("账户余额：" + new Okhttp().getResult(url, json));
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_query:
                tv_balance.setText("账户余额：" + new Okhttp().getResult(url, json));
                break;
            case R.id.btn_recharge:
                if (et_recharge.getText() != null
                        && 0 < Integer.parseInt(et_recharge.getText().toString())
                        && Integer.parseInt(et_recharge.getText().toString()) < 999) {
                    url = "http://192.168.0.74:8890/type/jason/action/SetCarAccountRecharge.do";
                    json = "{'CarId':" + sp_carno.getSelectedItemPosition()
                            + ",'Money'" + et_recharge.getText().toString() + "}";

                    if ("{'result':'ok'}".equals(new Okhttp().getResult(url, json))) {

                        //这里的数据库在登录或者注册时就应该创建的
                        helper = new MyDatabaseHelper(getContext(), "ETC.db", null, 1);
                        helper.getWritableDatabase();
                        SQLiteDatabase db = helper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("car_no", sp_carno.getSelectedItemPosition());
                        values.put("money", et_recharge.getText().toString());
                        values.put("operator", "1");
                        values.put("time", String.valueOf(Calendar.getInstance()));
                        db.insert("Recharge", null, values);

                        Toast.makeText(getContext(), sp_carno.getSelectedItemPosition() + "号车成功充值：" + et_recharge.getText() + "元", Toast.LENGTH_SHORT).show();
                        tv_balance.setText("账户余额：" + new Okhttp().getResult(url, "{'CarId':" + sp_carno.getSelectedItemPosition() + "}"));
                    } else {
                        Toast.makeText(getContext(), "充值失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "必需输入1-999的整数", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
