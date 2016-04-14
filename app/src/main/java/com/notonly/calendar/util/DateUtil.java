package com.notonly.calendar.util;

import java.util.Calendar;

/**
 * 日期工具类
 * Created by wangzhen on 16/1/13.
 */
public class DateUtil {

    static Calendar mCalendar = Calendar.getInstance();

    /**
     * 获取日期
     *
     * @return
     */
    public static String getDatetime() {
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + month + "-" + day;
    }

    /**
     * 获取年份
     *
     * @return
     */
    public static String getYear() {
        return String.valueOf(mCalendar.get(Calendar.YEAR));
    }

    /**
     * 获取月份
     *
     * @return
     */
    public static String getMonth() {
        return String.valueOf(mCalendar.get(Calendar.MONTH) + 1);
    }

    /**
     * 获取当月第几天
     *
     * @return
     */
    public static String getDay() {
        return String.valueOf(mCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
