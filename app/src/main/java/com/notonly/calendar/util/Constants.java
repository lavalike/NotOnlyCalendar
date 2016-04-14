package com.notonly.calendar.util;

/**
 * Created by wangzhen on 16/1/12.
 */
public class Constants {

    /**
     * 万年历 AppKey
     */
    public static String AppKey_Calendar = "8b1fc95fd35a9f2ebdef438dc8fb3aba";

    /**
     * 历史上的今天 AppKey
     */
    public static String AppKey_todayinhistory = "9cf7fd27641553195bb9c4a99c2ba59a";

    /**
     * Bmob AppKey
     */
    public static String AppKey_bmob = "501a9b6f002895753d000a30e8ee983d";

    /**
     * 万年历 URL
     *
     * @param date
     * @param key
     */
    public static String url_calendar = "http://japi.juhe.cn/calendar/day";
    /**
     * 历史上的今天 URL
     *
     * @param key
     * @param v 版本默认1.0
     * @param month
     * @param day
     */
    public static String url_todayinhistory = "http://api.juheapi.com/japi/toh";

    /**
     * 微信订阅号二维码
     */
    //微信官方地址 https://mp.weixin.qq.com/misc/getqrcode?fakeid=3075628606&token=1460165727&style=1
    //美女 http://c.hiphotos.baidu.com/image/pic/item/5bafa40f4bfbfbed91fbb0837ef0f736aec31faf.jpg
    //360云盘备份 http://t42-4.yunpan.360.cn/p2/800-600.90cd542580958df5c9208349d0a635fa9ac143fb_zzzc_42_zzzc1_t.4c9a83.jpg?st=iXTPqZBPKq6VyxidQnKgzQ&e=1455248270
    public static String url_weixin_qrcode = "http://d.pcs.baidu.com/thumbnail/1484522373e72d062c4e34c910ab6d28?fid=1982300563-250528-182735260404863&time=1452654000&rt=pr&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-A0NaK9MCp8TPJC5kEQZbjlpoR0w%3d&expires=8h&chkbd=0&chkv=0&dp-logid=289458260389432725&dp-callid=0&size=c1680_u1050&quality=90";

}
