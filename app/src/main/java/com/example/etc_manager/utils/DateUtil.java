package com.example.etc_manager.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public String getDate() {
        return new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒", Locale.CHINESE).format(new Date());
    }
}
