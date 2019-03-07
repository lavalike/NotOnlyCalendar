package com.notonly.calendar.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
     * 获取指定格式的日期字符串
     *
     * @param format 格式
     * @return 日期
     */
    public static String formatDateTime(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取年份
     *
     * @return year
     */
    public static String getYear() {
        return String.valueOf(mCalendar.get(Calendar.YEAR));
    }

    /**
     * 获取月份
     *
     * @return month
     */
    public static String getMonth() {
        return String.valueOf(mCalendar.get(Calendar.MONTH) + 1);
    }

    /**
     * 获取当月第几天
     *
     * @return day
     */
    public static String getDay() {
        return formatDateTime("dd");
    }
}
