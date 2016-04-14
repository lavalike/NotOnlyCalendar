package com.notonly.calendar.util;

import android.app.Application;

import org.xutils.x;

/**
 * Created by wangzhen on 16/1/12.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//初始化 xUtils3
    }
}
