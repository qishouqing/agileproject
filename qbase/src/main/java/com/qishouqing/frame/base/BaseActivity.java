package com.qishouqing.frame.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.qishouqing.frame.utils.LogUtils;
import com.qishouqing.frame.view.waitdialog.LoadingDialog;
import com.umeng.analytics.MobclickAgent;
import com.zhy.m.permission.MPermissions;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：qishouqing
 * 时间：2018/2/7 10:09
 * 邮箱：qi_shouqing@163.com
 */

public abstract class BaseActivity extends AppCompatActivity {

    public static final int PERMISS_CODE = 0;

    protected Activity mContext;

    protected Unbinder unbinder;
    private LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mContext = this;

        // 设置横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //手动统计页面("getPageName()"为页面名称，可自定义)
        MobclickAgent.onPageStart(getPageName());
        MobclickAgent.onResume(this);
        setFontScaleShow();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //手动统计页面("getPageName()"为页面名称，可自定义)
        MobclickAgent.onPageEnd(getPageName());
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }

        hideLoading();
    }

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.get(this);
        }
        loadingDialog.show();
    }


    public void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.hide();
        }
    }

    @Override
    public QApp getApplicationContext() {
        return (QApp) super.getApplicationContext();
    }

    // 6.0权限
    // 没有这个不会执行 成功或失败的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void bindViews() {
        unbinder = ButterKnife.bind(this);
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

    protected boolean translucentStatusBar() {
        return false;
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

    public String getPageName() {
        return this.getClass().getName();
    }

}
