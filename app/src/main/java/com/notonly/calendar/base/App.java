package com.notonly.calendar.base;

import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by wangzhen on 16/2/23.
 */
public class App extends MultiDexApplication {
    private static App mContext;
    private static Handler mainHandler = new Handler();

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Handler getMainHandler() {
        return mainHandler;
    }

    public static App getContext() {
        return mContext;
    }
}
