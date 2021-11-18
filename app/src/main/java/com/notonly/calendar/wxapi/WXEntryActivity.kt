package com.notonly.calendar.wxapi

import android.app.Activity
import android.content.Context
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.IWXAPI
import android.os.Bundle
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.notonly.calendar.base.helper.APIKey
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram

/**
 * 微信分享回调页面
 * Created by wangzhen on 16/4/15.
 */
class WXEntryActivity : Activity(), IWXAPIEventHandler {
    private var mContext: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        WXAPIFactory.createWXAPI(mContext, APIKey.AppID_WX, false).apply {
            handleIntent(intent, this@WXEntryActivity)
        }
    }

    override fun onReq(req: BaseReq) {}
    override fun onResp(resp: BaseResp) {
        if (resp.type == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            val launchMiniProResp = resp as WXLaunchMiniProgram.Resp
            //对应小程序组件 <button open-type="launchApp"> 中的 app-parameter 属性
            val extraData = launchMiniProResp.extMsg
        }
    }
}