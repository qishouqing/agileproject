package com.qishouqing.frame.utils.file;

import com.qishouqing.frame.utils.LogUtils;

import java.io.Closeable;
import java.io.IOException;



public class IOUtils {
    /**
     * 关闭文件流操作
     */
    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                LogUtils.stackTrace(e);
            }
        }
        return true;
    }
}
