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
     *
     * @method get
     */
    public static String URL_SLOGAN = "http://open.iciba.com/dsapi/";

    /**
     * 微信订阅号二维码
     */
    public static String URL_WEIXIN_QRCODE = "https://ws2.sinaimg.cn/large/006tNc79ly1fzgtb64mfuj3076076gm2.jpg";
}
