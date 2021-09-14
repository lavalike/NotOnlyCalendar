package com.notonly.calendar.util

import android.content.Context
import android.widget.Toast
import com.notonly.calendar.base.helper.APIKey
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * WxUtils
 * Created by wangzhen on 2020/7/7.
 */
object WxUtils {
    /**
     * 打开微信小程序
     *
     * @param context context
     */
    fun openMiniProgram(context: Context) {
        val api = WXAPIFactory.createWXAPI(context, APIKey.AppID_WX)
        if (!api.isWXAppInstalled) {
            Toast.makeText(context, "请先安装微信", Toast.LENGTH_SHORT).show()
            return
        }
        api.sendReq(WXLaunchMiniProgram.Req().apply {
            userName = APIKey.MINI_APP_ID
            miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE
        })
    }
}