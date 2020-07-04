package com.notonly.calendar.wxapi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.notonly.calendar.base.helper.APIKey;
import com.notonly.calendar.util.T;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

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
        api = WXAPIFactory.createWXAPI(mContext, APIKey.AppID_WX, false);
        api.registerApp(APIKey.AppID_WX);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) resp;
            //对应小程序组件 <button open-type="launchApp"> 中的 app-parameter 属性
            String extraData =launchMiniProResp.extMsg;
        }
    }

}

