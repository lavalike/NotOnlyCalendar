package com.notonly.calendar.domain

/**
 * Created by wangzhen on 16/1/12.
 */
class CalendarBean {
    /**
     * code : 1
     * msg : 数据返回成功
     * data : {"date":"2019-03-07","weekDay":4,"yearTips":"己亥","type":0,"typeDes":"工作日","chineseZodiac":"猪","solarTerms":"惊蛰后","avoid":"嫁娶.动土.掘井.起基.定磉.破土","lunarCalendar":"二月初一","suit":"祭祀.会亲友.出行.立券.交易.冠笄.纳财","dayOfYear":66,"weekOfYear":10}
     */
    var code = 0
    var msg: String? = null
    var data: DataBean? = null

    class DataBean {
        /**
         * date : 2019-03-07
         * weekDay : 4
         * yearTips : 己亥
         * type : 0
         * typeDes : 工作日
         * chineseZodiac : 猪
         * solarTerms : 惊蛰后
         * avoid : 嫁娶.动土.掘井.起基.定磉.破土
         * lunarCalendar : 二月初一
         * suit : 祭祀.会亲友.出行.立券.交易.冠笄.纳财
         * dayOfYear : 66
         * weekOfYear : 10
         */
        var date: String? = null
        var weekDay = 0
        var yearTips: String? = null
        var type = 0
        var typeDes: String? = null
        var chineseZodiac: String? = null
        var solarTerms: String? = null
        var avoid: String? = null
        var lunarCalendar: String? = null
        var suit: String? = null
        var dayOfYear = 0
        var weekOfYear = 0
        val weekDayCN: String
            get() = when (weekDay) {
                1 -> "星期一"
                2 -> "星期二"
                3 -> "星期三"
                4 -> "星期四"
                5 -> "星期五"
                6 -> "星期六"
                7 -> "星期日"
                else -> ""
            }
    }
}