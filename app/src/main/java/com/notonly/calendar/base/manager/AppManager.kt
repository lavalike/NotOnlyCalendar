package com.notonly.calendar.base.manager

import android.app.Activity
import java.util.*

/**
 * 管理所有Activity
 * Created by wangzhen on 16/10/20.
 */
class AppManager {

    fun addActivity(activity: Activity) {
        if (mActivityStack == null)
            mActivityStack = Stack()
        mActivityStack!!.push(activity)
    }

    fun removeTop() {
        mActivityStack?.pop()
    }

    fun removeActivity(activity: Activity) {
        mActivityStack?.let { stack ->
            if (stack.contains(activity))
                stack.remove(activity)
        }
    }

    fun removeAllActivity() {
        mActivityStack?.clear()
    }

    val appName: String
        get() {
            var appName = ""
            try {
                mActivityStack?.peek()?.let { activity ->
                    appName = activity.applicationInfo.loadLabel(activity.packageManager).toString()
                }
            } catch (ignored: Exception) {

            }
            return appName
        }

    /**
     * 退出整个应用
     */
    fun quit() {
        if (mActivityStack != null) {
            for (activity in mActivityStack!!) {
                activity.finish()
            }
            mActivityStack!!.clear()
        }
    }

    companion object {
        private var instance: AppManager? = null
        private var mActivityStack: Stack<Activity>? = null

        @JvmStatic
        fun get(): AppManager {
            if (instance == null) {
                synchronized(AppManager::class.java) {
                    instance = AppManager()
                }
            }
            return instance!!
        }
    }
}