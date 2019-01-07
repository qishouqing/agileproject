package com.qishouqing.frame.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.qishouqing.frame.R;
import com.qishouqing.frame.base.Config;
import com.qishouqing.frame.utils.SPUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpayentry);

        api = WXAPIFactory.createWXAPI(this, Config.APPID_WEIXIN);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        //0成功，展示成功页面
        //-1错误，可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
        //-2用户取消，无需处理。发生场景：用户不支付了，点击取消，返回APP。

        SPUtils.save(this, "weixinpay", "1");
        switch (resp.errCode) {
            case 0:   //支付成功
                SPUtils.save(this, "status", "0");
                break;
            case -1:  //支付失败
                SPUtils.save(this, "status", "-1");
                break;
            case -2:  //用户取消
                SPUtils.save(this, "status", "-2");
                break;
        }

        finish();
    }
}
