package com.notonly.calendar.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import com.wangzhen.commons.utils.AppUtils;

import java.util.List;
import java.util.UUID;

/**
 * 网络工具类
 *
 * @author Administrator
 * @since 2015年10月9日 15:28:08
 */
public class Utils {

    /**
     * 获取当前进程名字
     *
     * @return null may be returned if the specified process not found
     */
    public static String getProcessName(int pid) {
        ActivityManager am = (ActivityManager) AppUtils.getContext().getSystemService(Context
                .ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     * 不需要权限的唯一设备号
     * 返回 pseudo unique ARTICLE_ID
     * 支持API 9 以上
     * 每秒1兆的值（一万亿），需要100亿年才有可能发生重复  覆盖率为98.4%，剩下的为在系统9以下
     *
     * @return ARTICLE_ID
     */
    public static String getUniquePsuedoID() {
        String m_szDevIDShort = "24" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10)
                + (Build.CPU_ABI.length() % 10) + (Build.DEVICE
                .length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() %
                10) + (Build.PRODUCT.length() % 10);
        String serial = null;
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception e) {
            serial = "serial"; // some value
        }
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }
}
