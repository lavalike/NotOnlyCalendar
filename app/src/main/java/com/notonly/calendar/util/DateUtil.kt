package com.notonly.calendar.util

import com.notonly.calendar.util.DateUtil
import java.text.SimpleDateFormat
import java.util.*

/**
 * 日期工具类
 * Created by wangzhen on 16/1/13.
 */
object DateUtil {
    private var mCalendar = Calendar.getInstance()

    /**
     * 获取日期
     *
     * @return
     */
    val datetime: String
        get() {
            val year = mCalendar[Calendar.YEAR]
            val month = mCalendar[Calendar.MONTH] + 1
            val day = mCalendar[Calendar.DAY_OF_MONTH]
            return "$year-$month-$day"
        }

    /**
     * 获取指定格式的日期字符串
     *
     * @param format 格式
     * @return 日期
     */
    fun formatDateTime(format: String?): String {
        val simpleDateFormat = SimpleDateFormat(format, Locale.CHINA)
        return simpleDateFormat.format(Date())
    }

    /**
     * 获取年份
     *
     * @return year
     */
    val year: String
        get() = mCalendar[Calendar.YEAR].toString()

    /**
     * 获取月份
     *
     * @return month
     */
    val month: String
        get() = (mCalendar[Calendar.MONTH] + 1).toString()

    /**
     * 获取当月第几天
     *
     * @return day
     */
    val day: String
        get() = formatDateTime("dd")
}