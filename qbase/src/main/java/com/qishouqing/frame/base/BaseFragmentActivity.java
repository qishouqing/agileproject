package com.qishouqing.frame.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.qishouqing.frame.utils.LogUtils;
import com.qishouqing.frame.utils.toast.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragmentActivity extends FragmentActivity {

    public static final int REQUEST_LOGIN = 1;
    protected Unbinder unbinder;
    protected Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void bindViews() {
        unbinder = ButterKnife.bind(this);
    }

    public void snackBarAlert(String msg, int duration) {
        ToastUtil.showToastSetDuration(msg, duration);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }



    public void back(View v) {
        finish();
    }

    public void hideKeyBoard() {
        try {
            InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (methodManager != null)
                methodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        } catch (Exception e) {
            LogUtils.stackTrace(e);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        setFontScaleShow();
    }

    /**
     * 设置字体不随系统字体大小变化
     */
    private void setFontScaleShow() {
        // 控制字体不随系统设置变化，避免字体变化导致的布局错乱的问题
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = 1.0f;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public String getPageName() {
        return this.getClass().getName();
    }

}
