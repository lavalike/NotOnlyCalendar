package com.notonly.calendar.util;

import android.content.Context;

import com.notonly.calendar.base.BaseApplication;

/**
 * UI工具类
 * Created by wangzhen on 2018/8/27.
 */
public class UIUtils {

    private UIUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static BaseApplication mApp;

    public static void init(BaseApplication application) {
        mApp = application;
    }

    /**
     * 获取Application
     *
     * @return
     */
    public static BaseApplication getApp() {
        return mApp;
    }

    public static Context getContext() {
        return mApp;
    }
}
