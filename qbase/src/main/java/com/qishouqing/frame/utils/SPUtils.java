package com.qishouqing.frame.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
    private static final String FILENAME = "sp";

    public SPUtils() {
    }

    public static void save(Context con, String key, String value) {
        SharedPreferences sp = con.getSharedPreferences("sp", 0);
        sp.edit().putString(key, value).commit();
    }

    public static String get(Context con, String key) {
        SharedPreferences sp = con.getSharedPreferences("sp", 0);
        return sp.getString(key, (String)null);
    }
}
