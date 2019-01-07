package com.qishouqing.frame;

import android.os.Bundle;

import com.qishouqing.frame.base.BaseActivity;

import butterknife.ButterKnife;

public class TestActivity3 extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        ButterKnife.bind(this);
        bindViews();

    }
}
