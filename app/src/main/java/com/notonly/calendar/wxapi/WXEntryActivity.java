package com.notonly.calendar.wxapi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.notonly.calendar.util.APIManager;
import com.notonly.calendar.util.ToastUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信分享回调页面
 * Created by wangzhen on 16/4/15.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private Context mContext;
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        api = WXAPIFactory.createWXAPI(mContext, APIManager.AppID_WX, false);
        api.registerApp(APIManager.AppID_WX);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq arg0) {

    }

    @Override
    public void onResp(BaseResp arg0) {
        String result;
        switch (arg0.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "分享成功";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "分享被取消";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "分享被拒绝";
                break;

            default:
                result = "返回";
                break;
        }
        ToastUtil.getInstance(mContext).toast(result);
        finish();
    }

}

