package com.example.etc_manager.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_RECHARGE = "create table Recharge ("
            + "id integer primary key autoincrement,"
            + "car_no integer,"
            + "money integer,"
            + "operator text,"
            + "time date)";

    private static final String CREATE_USER = "create table User ("
            + "uid text primary key,"
            + "pass text,"
            + "idNo text,"
            + "name text,"
            + "gender text,"
            + "tel text)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECHARGE);
        db.execSQL(CREATE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
