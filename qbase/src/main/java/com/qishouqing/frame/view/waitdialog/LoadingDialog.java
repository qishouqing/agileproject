package com.qishouqing.frame.view.waitdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.qishouqing.frame.R;
import com.qishouqing.frame.utils.UIUtils;

public class LoadingDialog {
    private Dialog dialog;
    private Context context;
    private int active = 1;

    private LoadingDialog(Context context) {
        this.context = context;
        init();
    }

    public static LoadingDialog get(Context context) {
        return new LoadingDialog(context);
    }

    private void init() {
        FrameLayout container = new FrameLayout(context);
        //second container.
        FrameLayout secondContainer = new FrameLayout(context);
        FrameLayout.LayoutParams containerParam = new FrameLayout.LayoutParams(UIUtils.dip2px(56), UIUtils.dip2px(56));
        containerParam.gravity = Gravity.CENTER;
        container.addView(secondContainer, containerParam);
        //loading view
        LoadingView loadingView = new LoadingView(context);
        loadingView.setColor(Color.argb(255, 55, 55, 55));
        loadingView.setIndicator(LoadingView.BallSpinFadeLoader);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        secondContainer.addView(loadingView, params);
        secondContainer.setPadding(UIUtils.dip2px(10), UIUtils.dip2px(10), UIUtils.dip2px(10), UIUtils.dip2px(10));
        secondContainer.setBackground(context.getResources().getDrawable(R.drawable.dl_bg_white_radius));
        secondContainer.setAlpha(0.8f);
        dialog = new AlertDialog.Builder(context).setView(container).create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
            window.getAttributes().gravity = Gravity.CENTER;
            window.getAttributes().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            window.getAttributes().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setDimAmount(0);
        }
    }

    public void show() {
        if (dialog.isShowing()) {
            ++active;
        } else {
            dialog.show();
            active = 1;
        }
    }

    public void hide() {
        if (dialog.isShowing()) {
            if (active > 1) {
                --active;
            } else {
                dialog.dismiss();
                active = 0;
            }
        }
    }

    public Dialog getDialog() {
        return dialog;
    }
}
