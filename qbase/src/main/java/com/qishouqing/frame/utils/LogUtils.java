package com.qishouqing.frame.utils;

import android.text.TextUtils;
import android.util.Log;

import com.qishouqing.frame.BuildConfig;

import java.util.List;



/**
 * 日志输出控制类 (Description)
 *
 * @author peter
 */
public class LogUtils {

    private static final String FILETER_STRING = "##";

    /**
     * 日志输出时的TAG
     */
    private static String mTag = "##UNICORN";

    /**
     * 日志输出级别NONE
     */
    public static final int LEVEL_NONE = 0;
    /**
     * 日志输出级别V
     */
    public static final int LEVEL_VERBOSE = 1;
    /**
     * 日志输出级别D
     */
    public static final int LEVEL_DEBUG = 2;
    /**
     * 日志输出级别I
     */
    public static final int LEVEL_INFO = 3;
    /**
     * 日志输出级别W
     */
    public static final int LEVEL_WARN = 4;
    /**
     * 日志输出级别E
     */
    public static final int LEVEL_ERROR = 5;
    /**
     * 是否允许输出log
     */
    private static int mDebuggable = LEVEL_ERROR;
    /**
     * 写文件的锁对象
     */
    private static final Object mLogLock = new Object();

    /**
     * 用于记时的变量
     */
    private static long mTimestamp = 0;

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void v(String msg) {
        if (mDebuggable >= LEVEL_VERBOSE) {
            Log.v(FILETER_STRING + mTag, msg);
        }
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void d(String msg) {
        if (mDebuggable >= LEVEL_DEBUG) {
            Log.d(FILETER_STRING + mTag, msg);
        }
    }

    /**
     * 以级别为 i 的形式输出LOG
     */
    public static void i(String msg) {
        if (mDebuggable >= LEVEL_INFO) {
            Log.i(FILETER_STRING + mTag, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG
     */
    public static void w(String msg) {
        if (mDebuggable >= LEVEL_WARN) {
            Log.w(FILETER_STRING + mTag, msg);
        }
    }

    /**
     * 以级别为 w 的形式输出Throwable
     */
    public static void w(Throwable tr) {
        if (mDebuggable >= LEVEL_WARN) {
            Log.w(FILETER_STRING + mTag, "", tr);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG信息和Throwable
     */
    public static void w(String msg, Throwable tr) {
        if (mDebuggable >= LEVEL_WARN && null != msg) {
            Log.w(FILETER_STRING + mTag, msg, tr);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(String msg) {
        if (mDebuggable >= LEVEL_ERROR) {
            Log.e(FILETER_STRING + mTag, msg);
        }
    }

    /**
     * 以级别为 e 的形式输出Throwable
     */
    public static void e(Throwable tr) {
        if (mDebuggable >= LEVEL_ERROR) {
            Log.e(FILETER_STRING + mTag, "", tr);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG信息和Throwable
     */
    public static void e(String msg, Throwable tr) {
        if (mDebuggable >= LEVEL_ERROR && null != msg) {
            Log.e(FILETER_STRING + mTag, msg, tr);
        }
    }

    /**
     * 以级别为 e 的形式输出msg信息,附带时间戳，用于输出一个时间段起始点
     *
     * @param msg 需要输出的msg
     */
    public static void msgStartTime(String msg) {
        mTimestamp = System.currentTimeMillis();
        if (!TextUtils.isEmpty(msg)) {
            e("[Started：" + mTimestamp + "]" + msg);
        }
    }

    /**
     * 以级别为 e 的形式输出msg信息,附带时间戳，用于输出一个时间段结束点* @param msg 需要输出的msg
     */
    public static void elapsed(String msg) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - mTimestamp;
        mTimestamp = currentTime;
        e("[Elapsed：" + elapsedTime + "]" + msg);
    }

    public static <T> void printList(List<T> list) {
        if (list == null || list.size() < 1) {
            return;
        }
        int size = list.size();
        i("---begin---");
        for (int i = 0; i < size; i++) {
            i(i + ":" + list.get(i).toString());
        }
        i("---end---");
    }

    public static <T> void printArray(T[] array) {
        if (array == null || array.length < 1) {
            return;
        }
        int length = array.length;
        i("---begin---");
        for (int i = 0; i < length; i++) {
            i(i + ":" + array[i].toString());
        }
        i("---end---");
    }

    public static void V(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.v(FILETER_STRING + tag, msg);
        }
    }

    public static void D(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(FILETER_STRING + tag, msg);
        }
    }

    public static void I(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(FILETER_STRING + tag, msg);
        }
    }

    public static void W(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(FILETER_STRING + tag, msg);
        }
    }

    public static void E(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(FILETER_STRING + tag, msg);
        }
    }

    public static void E(String tag, String msg, Exception e) {
        if (BuildConfig.DEBUG) {
            Log.e(FILETER_STRING + tag, msg + " : " + (e != null ? e.getMessage() : "null"));
            if (e != null) {
                e.printStackTrace();
            }
        }
    }

    public static void stackTrace(Exception e) {
        if (BuildConfig.DEBUG) {
            if (e != null) {
                e.printStackTrace();
            }
        }
    }
}
