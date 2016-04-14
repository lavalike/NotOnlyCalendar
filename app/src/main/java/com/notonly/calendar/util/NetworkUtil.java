package com.notonly.calendar.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络工具类
 * 
 * @author Administrator
 * @since 2015年10月9日 15:28:08
 */
public class NetworkUtil {

	/**
	 * 获取当前版本号
	 * 
	 * @param context
	 * @return
	 */
	public static String getAppVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "0.0.0";
	}

	/**
	 * 判断网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (manager == null) {
			return false;
		}
		// 获取NetworkInfo对象
		NetworkInfo[] networkInfo = manager.getAllNetworkInfo();
		if (networkInfo != null && networkInfo.length > 0) {
			for (int i = 0; i < networkInfo.length; i++) {
				// 判断当前网络状态是否为连接状态
				if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}
}
