package com.notonly.calendar.base

import android.os.Process
import androidx.multidex.MultiDexApplication
import com.notonly.calendar.base.manager.APIManager
import com.notonly.calendar.network.RequestInterceptor
import com.notonly.calendar.util.Utils
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.BuildConfig
import com.umeng.commonsdk.UMConfigure
import com.wangzhen.commons.utils.AppUtils
import com.wangzhen.network.Network
import com.wangzhen.network.config.NetConfig

/**
 * Created by wangzhen on 16/2/23.
 */
class BaseApplication : MultiDexApplication() {
    private val channel = "only"

    override fun onCreate() {
        super.onCreate()
        AppUtils.install(this, true)
        initUmeng()
        initBugly()
        Network.init(
            NetConfig.Builder()
                .baseUrl(APIManager.baseUrl)
                .interceptor(RequestInterceptor())
                .build()
        )
    }

    /**
     * 初始化bugly
     */
    private fun initBugly() {
        val processName = Utils.getProcessName(Process.myPid())
        val strategy = UserStrategy(this)
        strategy.isUploadProcess = processName == null || processName == packageName
        strategy.deviceID = Utils.uniquePsuedoID()
        strategy.appChannel = channel
        CrashReport.initCrashReport(this, "38f2ae3fad", BuildConfig.DEBUG, strategy)
    }

    /**
     * 初始化友盟
     */
    private fun initUmeng() {
        /**
         * 设置日志加密
         * 参数：boolean 默认为false（不加密）
         */
        UMConfigure.setEncryptEnabled(true)
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true)
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空。
         */
        UMConfigure.init(
            this,
            "5b83ad44b27b0a5719000017",
            channel,
            UMConfigure.DEVICE_TYPE_PHONE,
            null
        )

        //旧版本SDK统计方法
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL)
        MobclickAgent.openActivityDurationTrack(false)
    }
}