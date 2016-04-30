package com.notonly.calendar.util;

import android.app.Activity;
import android.app.Application;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhen on 16/1/12.
 */
public class App extends Application {

    private static App mInstance;
    private List<Activity> mActivityList;


    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//初始化 xUtils3
        mActivityList = new ArrayList<>();
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static App getInstance() {
        if (mInstance == null) {
            synchronized (App.class) {
                mInstance = new App();
            }
        }
        return mInstance;

    }


    /**
     * 记录Activity
     */
    public void addActivity(Activity activity) {
        if (mActivityList != null) {
            mActivityList.add(activity);
        }
    }

    /**
     * 退出所有Activity
     */
    public void exit() {
        if (mActivityList != null) {
            for (Activity activity :
                    mActivityList) {
                activity.finish();
            }
            mActivityList.clear();
        }
    }
}
