package com.qishouqing.frame.utils.file;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileStaticUtils {

    public static final boolean isExternalMemoryAvailable() {
        return "mounted".equals(Environment.getExternalStorageState()) && Environment.getExternalStorageState() != null && "mounted_ro" != Environment.getExternalStorageState();
    }

    public static final String getTempFileDir(Context context) {
        String path = getFileDir(context);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        return path;
    }

    public static final String getFileDir(Context context) {
        String path = isExternalMemoryAvailable() ? context.getExternalFilesDir((String) null).getPath() + "/" : context.getFilesDir().getPath() + "/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        return path;
    }

    public static String getFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        return dateFormat.format(date);
    }
}
