package com.example.etc_manager.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.etc_manager.R;
import com.example.etc_manager.utils.MyDatabaseHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class F1_bill extends Fragment implements View.OnClickListener {
    private MyDatabaseHelper helper = new MyDatabaseHelper(getContext(), "Recharge.db", null, 1);
    private List<String> list = new ArrayList<>();
    private String orderBy;
    private Spinner spinner;
    private Button button;
    private ListView listView;

    public F1_bill() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_1_bill, container, false);
        spinner = v.findViewById(R.id.spinner_bill);
        String[] spinnerItems = {"时间降序", "时间升序"};
        ArrayAdapter<String> carAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                spinnerItems
        );
        spinner.setAdapter(carAdapter);
        button = v.findViewById(R.id.btn_bill_query);
        listView = v.findViewById(R.id.listView_bill);

        button.setOnClickListener(this);

        return v;
    }

    public void Query() {
        switch (spinner.getSelectedItemPosition()) {
            case 0:
                orderBy = "desc";
                break;
            case 1:
                orderBy = "asc";
                break;
        }

        list.add("序号    车号    充值金额（元）    操作人    充值时间");

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("Recharge", null, null, null, null, null, "time " + orderBy);
        if (cursor.moveToFirst()) {
            int i = 0;
            do {
                i++;
                String history =
                        cursor.getString(cursor.getColumnIndex("car_no"))
                                + "    " + cursor.getString(cursor.getColumnIndex("money"))
                                + "    " + cursor.getString(cursor.getColumnIndex("operator"))
                                + "    " + cursor.getString(cursor.getColumnIndex("time"));
                list.add(i + "    " + history);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void onClick(View v) {
        if (new File(Environment.getDataDirectory().getPath() + "database/Recharge.db").exists()) {
            Query();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getContext(), android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "暂无历史记录", Toast.LENGTH_SHORT).show();
        }
    }
}
