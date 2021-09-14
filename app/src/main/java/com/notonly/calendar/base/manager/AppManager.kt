package com.notonly.calendar.base.manager;

import android.app.Activity;
import android.content.pm.PackageManager;

import java.util.Stack;

/**
 * 管理所有Activity
 * Created by wangzhen on 16/10/20.
 */

public class AppManager {
    private static AppManager mInstance;
    /**
     * 存储所有打开的Activity
     */
    private static Stack<Activity> mActivityStack;

    public static AppManager get() {
        if (mInstance == null) {
            synchronized (AppManager.class) {
                mInstance = new AppManager();
            }
        }
        return mInstance;
    }

    /**
     * 获取栈顶元素
     *
     * @return
     */
    public Activity getActivity() {
        if (mActivityStack != null) {
            return mActivityStack.peek();
        }
        return null;
    }

    /**
     * 添加Activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null)
            mActivityStack = new Stack<>();
        mActivityStack.push(activity);
    }

    /**
     * 移除顶部Activity
     */
    public void removeTop() {
        if (mActivityStack != null) {
            mActivityStack.pop();
        }
    }

    /**
     * 删除Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (mActivityStack != null) {
            if (mActivityStack.contains(activity)) {
                mActivityStack.remove(activity);
            }
        }
    }

    /**
     * 清空所有Activity
     */
    public void removeAllActivity() {
        if (mActivityStack != null) {
            mActivityStack.clear();
        }
    }

    /**
     * 获取App名称
     */
    public String getAppName() {
        String appName = "";
        try {
            Activity activity = getActivity();
            if (activity != null) {
                PackageManager pm = activity.getPackageManager();
                appName = activity.getApplicationInfo().loadLabel(pm)
                        .toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appName;
    }

    /**
     * 退出整个应用
     */
    public void quit() {
        if (mActivityStack != null) {
            for (Activity activity : mActivityStack) {
                activity.finish();
            }
            mActivityStack.clear();
        }
    }
}
