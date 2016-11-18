package com.notonly.calendar.base.manager;

/**
 * APIManager
 * Created by wangzhen on 16/1/12.
 */
public class APIManager {

    /**
     * 万年历
     *
     * @param date
     * @param key
     */
    public static String URL_CALENDAR = "http://japi.juhe.cn/calendar/day";
    /**
     * 历史上的今天 V1.0
     *
     * @param key
     * @param v 版本默认1.0
     * @param month
     * @param day
     */
    public static String URL_TODAY_ON_HISTORY = "http://api.juheapi.com/japi/toh";

    /**
     * 历史上的今天详情 V1.0
     *
     * @param key
     * @param v 版本默认1.0
     * @param id
     */
    public static String URL_HISTORY_DETAIL = "http://api.juheapi.com/japi/tohdet";

    /**
     * 历史上的今天 V2.0
     * GET请求
     *
     * @param date 11/18
     * @param key
     */
    public static String URL_TODAY_ON_HISTORY_V2 = "http://v.juhe.cn/todayOnhistory/queryEvent.php";

    /**
     * 历史上的今天详情 V2.0
     * GET请求
     *
     * @param e_id 事件id
     * @param key
     */
    public static String URL_HISTORY_DETAIL_V2 = "http://v.juhe.cn/todayOnhistory/queryDetail.php";

    /**
     * 微信订阅号二维码
     */
    //微信官方地址 https://mp.weixin.qq.com/misc/getqrcode?fakeid=3075628606&token=1460165727&style=1
    //美女 http://c.hiphotos.baidu.com/image/pic/item/5bafa40f4bfbfbed91fbb0837ef0f736aec31faf.jpg
    //360云盘备份 http://t42-4.yunpan.360.cn/p2/800-600.90cd542580958df5c9208349d0a635fa9ac143fb_zzzc_42_zzzc1_t.4c9a83.jpg?st=iXTPqZBPKq6VyxidQnKgzQ&e=1455248270
    //百度云盘 http://d.pcs.baidu.com/thumbnail/1484522373e72d062c4e34c910ab6d28?fid=1982300563-250528-182735260404863&time=1452654000&rt=pr&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-A0NaK9MCp8TPJC5kEQZbjlpoR0w%3d&expires=8h&chkbd=0&chkv=0&dp-logid=289458260389432725&dp-callid=0&size=c1680_u1050&quality=90
    //https://github.com/lavalike/PictureBed/raw/master/NotOnlyCalendar/qrcode_weixin.jpg
//    public static String url_weixin_qrcode = "http://i3.piimg.com/73e72d062c4e34c9.jpg";
    public static String url_weixin_qrcode = "http://p1.bpimg.com/567571/4ff93f8d2ddeb083.jpg";

}
