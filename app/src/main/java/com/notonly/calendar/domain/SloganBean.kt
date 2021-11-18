package com.notonly.calendar.domain

import java.io.Serializable

/**
 * 首页欢迎语
 * Created by wangzhen on 2017/4/24.
 */
class SloganBean : BaseBean() {
    /**
     * sid : 2577
     * tts : http://news.iciba.com/admin/tts/2017-04-24-day.mp3
     * content : A little sincerity is a dangerous thing, and a great deal of it is absolutely fatal. -  Wilde
     * note : 不够真诚是危险的，太真诚则绝对是致命的。——王尔德
     * love : 1283
     * translation : 词霸小编：【语法扩充】a lot of,lots of,plenty of,a great deal of 后面都接名词,可数不可数名词都可以,表示数量很多；a few 与 a little 表示不多的意思,a few后接可数名词,a little后接不可数名词；few 和 little 表示很少,几乎没有,含否定意义,同样是few接可数名词, little接不可数名词。
     * picture : http://cdn.iciba.com/news/word/20170424.jpg
     * picture2 : http://cdn.iciba.com/news/word/big_20170424b.jpg
     * caption : 词霸每日一句
     * dateline : 2017-04-24
     * s_pv : 0
     * sp_pv : 0
     * tags : [{"id":null,"name":null},{"id":null,"name":null}]
     * fenxiang_img : http://cdn.iciba.com/web/news/longweibo/imag/2017-04-24.jpg
     */
    var sid: String? = null
    var tts: String? = null
    var content: String? = null
    var note: String? = null
    var love: String? = null
    var translation: String? = null
    var picture: String? = null
    var picture2: String? = null
    var caption: String? = null
    var dateline: String? = null
    var s_pv: String? = null
    var sp_pv: String? = null
    var fenxiang_img: String? = null
    var tags: List<TagsBean>? = null

    class TagsBean : Serializable {
        /**
         * id : null
         * name : null
         */
        var id: Any? = null
        var name: Any? = null
    }
}