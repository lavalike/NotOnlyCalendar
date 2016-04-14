package com.notonly.calendar.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类(单例模式)
 *
 * @author administrator
 * @since 2015年9月17日 15:17:09
 */
public class ToastUtil {

    private static Context mContext;
    private static ToastUtil mInstance;
    private static Toast mToast;

    /**
     * 单例模式
     *
     * @param context
     * @return
     */
    public static ToastUtil getInstance(Context context) {
        if (mInstance == null || mToast == null) {
            synchronized (ToastUtil.class) {
                mContext = context;
                mInstance = new ToastUtil();
                mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
            }
        }
        return mInstance;
    }

    /**
     * 默认Toast
     *
     * @param msg
     */
    public void toast(String msg) {
        mToast.setText(msg);
        mToast.show();
    }

}
