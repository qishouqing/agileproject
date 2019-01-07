package com.qishouqing.frame.utils.toast;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dovar.dtoast.DToast;
import com.dovar.dtoast.inner.IToast;
import com.qishouqing.frame.R;
import com.qishouqing.frame.base.QApp;
import com.qishouqing.frame.utils.UIUtils;


/***
 * 包名：cn.com.dareway.unicorn.utils
 * 类名：ToastUtil
 * 时间：2018/10/24 10:49
 * 作者：ljm
 * 功能简述：Toast工具类，建议使用直接传String id的方法（showShort(int msgId)），
 * 其余参数不要传，这样可以避免内存泄漏，并可以把字符串资源统一放在string文件
 * 修改历史：
 */
public class ToastUtil {

    private static IToast toast = null;
    private static TextView tvToastMsg;

    private ToastUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    public static boolean isShow = true;

    /**
     * 短暂显示Toast
     *
     * @param message 显示内容(CharSequence)
     */
    public static void showShort(CharSequence message) {
        if (isShow) {
            checkToast();
            toast.setDuration(Toast.LENGTH_SHORT);
            showToast(message);
        }
    }

    /**
     * 短暂显示Toast
     *
     * @param msgId 显示内容(字符串ID)
     */
    public static void showShort(int msgId) {
        if (isShow) {
            checkToast();
            toast.setDuration(Toast.LENGTH_SHORT);
            showToast(msgId);
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param message 显示内容(CharSequence)
     */
    public static void showLong(CharSequence message) {
        if (isShow) {
            checkToast();
            toast.setDuration(DToast.DURATION_LONG);
            showToast(message);
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param msgId 显示内容(字符串ID)
     */
    public static void showLong(int msgId) {
        if (isShow) {
            checkToast();
            toast.setDuration(DToast.DURATION_LONG);
            showToast(msgId);
        }
    }

    /**
     * 自定义显示 Toast内容和时间
     *
     * @param message  显示内容(CharSequence)
     * @param duration 显示时间
     */
    public static void showToastSetDuration(CharSequence message, int duration) {
        if (isShow) {
            checkToast();
            toast.setDuration(duration);
            showToast(message);
        }
    }

    /**
     * 自定义显示 Toast内容和时间
     *
     * @param msgId    显示内容(字符串ID)
     * @param duration 显示时间
     */
    public static void showToastSetDuration(int msgId, int duration) {
        if (isShow) {
            checkToast();
            toast.setDuration(duration);
            showToast(msgId);
        }
    }

    /**
     * 显示Toast的内容
     *
     * @param message toast内容
     */
    private static void showToast(CharSequence message) {
        // 字符串为空时，不做提示
        if (TextUtils.isEmpty(message)) {
            return;
        }
        tvToastMsg.setText(message);
        toast.show();
    }

    /**
     * 显示Toast的内容
     *
     * @param msgId 字符串ID
     */
    private static void showToast(int msgId) {
        // 字符串为空时，不做提示
        if (TextUtils.isEmpty(UIUtils.getString(msgId))) {
            return;
        }
        tvToastMsg.setText(msgId);
        toast.show();
    }

    /**
     * 创建Toast,自定义显示样式
     */
    private static void newToast() {
        LayoutInflater inflater = (LayoutInflater) QApp.getInstance().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_toast, null);
        tvToastMsg = layout.findViewById(R.id.toastMsg);
        toast = DToast.make(QApp.getInstance());
        // 屏幕下方显示吐司
        toast.setGravity(Gravity.BOTTOM, 0, 180);
        toast.setView(layout);
    }

    /**
     * 检查Toast,如果已经显示，则取消掉显示最新的提示信息；
     * 如果是第一次显示，则先创建Toast后再使用
     */
    private static void checkToast() {
        if (toast == null) {
            newToast();
        }
    }

}