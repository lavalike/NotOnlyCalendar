package com.notonly.calendar.base;

import android.content.Context;
import android.os.Handler;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.notonly.calendar.base.manager.APIManager;
import com.notonly.calendar.network.RequestInterceptor;
import com.notonly.calendar.util.AppUtils;
import com.notonly.calendar.util.UIUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.BuildConfig;
import com.umeng.commonsdk.UMConfigure;
import com.wangzhen.network.Network;
import com.wangzhen.network.config.NetConfig;

/**
 * Created by wangzhen on 16/2/23.
 */
public class BaseApplication extends MultiDexApplication {
    private static BaseApplication mContext;
    private static Handler mainHandler = new Handler();
    private String channel = "only";

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        UIUtils.init(this);
        initUmeng();
        initBugly();
        Network.init(new NetConfig.Builder()
                .baseUrl(APIManager.getBaseUrl())
                .interceptor(new RequestInterceptor())
                .build());
    }

    /**
     * 初始化bugly
     */
    private void initBugly() {
        String processName = AppUtils.getProcessName(android.os.Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setUploadProcess(processName == null || processName.equals(getPackageName()));
        strategy.setDeviceID(AppUtils.getUniquePsuedoID());
        strategy.setAppChannel(channel);
        CrashReport.initCrashReport(this, "38f2ae3fad", BuildConfig.DEBUG, strategy);
    }

    /**
     * 初始化友盟
     */
    private void initUmeng() {
        /**
         * 设置日志加密
         * 参数：boolean 默认为false（不加密）
         */
        UMConfigure.setEncryptEnabled(true);
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空。
         */
        UMConfigure.init(this,
                "5b83ad44b27b0a5719000017",
                channel,
                UMConfigure.DEVICE_TYPE_PHONE,
                null);

        //旧版本SDK统计方法
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.openActivityDurationTrack(false);

    }

    public static Handler getMainHandler() {
        return mainHandler;
    }

    public static BaseApplication getContext() {
        return mContext;
    }
}
