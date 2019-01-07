package com.qishouqing.frame.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    private static final String TAG = "Debug_" + BaseFragment.class.getSimpleName();

    protected BaseFragmentActivity context;

    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
        } else {
            isVisible = false;
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayoutID(), container, false);
        ButterKnife.bind(this, view);
        onCreateView(savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (this.context == null) {
            this.context = (BaseFragmentActivity) context;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (this.context == null) {
            this.context = (BaseFragmentActivity) activity;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @LayoutRes
    protected abstract int setLayoutID();

    protected abstract void onCreateView(@Nullable Bundle savedInstanceState);

    @Override
    public void onResume() {
        super.onResume();
        isVisible = true;
        MobclickAgent.onPageStart(getPageName()); //统计页面("MainScreen"为页面名称，可自定义)
        // 控制字体不随系统设置变化，避免字体变化导致的布局错乱的问题
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = 1.0f;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public void onPause() {
        super.onPause();
        isVisible = false;
        MobclickAgent.onPageEnd(getPageName());
    }

    public String getPageName() {
        return this.getClass().getName();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVisible = !hidden;
    }
}
