package com.notonly.calendar.base.manager;

/**
 * APIManager
 * Created by wangzhen on 16/1/12.
 */
public class APIManager {

    public static final String APP_ID = "moehknojlpi9llvn";
    public static final String APP_SECRET = "aVBrU2pBSTJFY3c3aUZVMjZMbW1Wdz09";

    public static String getBaseUrl() {
        return "http://www.mxnzp.com/api/";
    }

    /**
     * 请求词霸每日一句
     */
    public static String URL_SLOGAN = "http://open.iciba.com/dsapi/";
}
