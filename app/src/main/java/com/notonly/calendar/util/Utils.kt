package com.notonly.calendar.util

import android.app.ActivityManager
import com.wangzhen.commons.utils.AppUtils
import android.content.Context
import android.os.Build
import java.lang.Exception
import java.util.*

/**
 * 网络工具类
 *
 * @author Administrator
 * @since 2015年10月9日 15:28:08
 */
object Utils {
    /**
     * 获取当前进程名字
     *
     * @return null may be returned if the specified process not found
     */
    fun getProcessName(pid: Int): String? {
        val am = AppUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningApps = am.runningAppProcesses ?: return null
        for (procInfo in runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName
            }
        }
        return null
    }// some value

    /**
     * 不需要权限的唯一设备号
     * 返回 pseudo unique ARTICLE_ID
     * 支持API 9 以上
     * 每秒1兆的值（一万亿），需要100亿年才有可能发生重复  覆盖率为98.4%，剩下的为在系统9以下
     *
     * @return ARTICLE_ID
     */
    fun uniquePsuedoID(): String {
        val short = ("24" + Build.BOARD.length % 10 + Build.BRAND.length % 10
                + Build.CPU_ABI.length % 10 + Build.DEVICE
            .length % 10 + Build.MANUFACTURER.length % 10 + Build.MODEL.length %
                10 + Build.PRODUCT.length % 10)
        var serial: String?
        try {
            serial = Build::class.java.getField("SERIAL")[null].toString()
            return UUID(
                short.hashCode().toLong(), serial.hashCode().toLong()
            ).toString()
        } catch (e: Exception) {
            serial = "serial" // some value
        }
        return UUID(short.hashCode().toLong(), serial.hashCode().toLong()).toString()
    }
}