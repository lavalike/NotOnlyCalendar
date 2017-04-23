package com.notonly.calendar.base.helper;

import com.notonly.calendar.R;
import com.notonly.calendar.base.App;
import com.notonly.calendar.util.AppUtils;
import com.notonly.calendar.util.T;

import java.net.SocketTimeoutException;

/**
 * 错误提示帮助类
 * Created by wangzhen on 2016/11/18.
 */

public class ErrHelper {
    private static App mContext;

    static {
        mContext = App.getContext();
    }

    /**
     * 解析错误类型
     *
     * @param throwable
     */
    public static void check(Throwable throwable) {
        if (!AppUtils.isNetworkAvailable(mContext)) {
            T.get(mContext).toast(mContext.getString(R.string.error_network));
            return;
        }
        if (throwable instanceof SocketTimeoutException) {
            T.get(mContext).toast(mContext.getString(R.string.error_connect_timeout));
            return;
        }
    }
}
