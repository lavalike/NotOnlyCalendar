package com.notonly.calendar.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by wangzhen on 16/2/23.
 */
public class App extends MultiDexApplication {
    private static App mInstance;
    //SQLite配置
    private static DbManager.DaoConfig db_config;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false);
    }

    /**
     * 获取Db的Config
     *
     * @return
     */
    public static DbManager.DaoConfig getDbConfig() {
        if (db_config == null) {
            synchronized (App.class) {
                db_config = new DbManager.DaoConfig().setDbName("Mobile8531.db");
            }
        }
        return db_config;
    }


    /**
     * 单例模式
     *
     * @return
     */
    public static App getInstance() {
        if (mInstance == null) {
            synchronized (App.class) {
                if (mInstance == null) {
                    mInstance = new App();
                }
            }
        }
        return mInstance;
    }

}
