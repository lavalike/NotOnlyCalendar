package com.notonly.calendar.domain;

/**
 * Created by wangzhen on 16/1/12.
 */
public class CalendarBean {

    /**
     * code : 1
     * msg : 数据返回成功
     * data : {"date":"2019-03-07","weekDay":4,"yearTips":"己亥","type":0,"typeDes":"工作日","chineseZodiac":"猪","solarTerms":"惊蛰后","avoid":"嫁娶.动土.掘井.起基.定磉.破土","lunarCalendar":"二月初一","suit":"祭祀.会亲友.出行.立券.交易.冠笄.纳财","dayOfYear":66,"weekOfYear":10}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
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

        private String date;
        private int weekDay;
        private String yearTips;
        private int type;
        private String typeDes;
        private String chineseZodiac;
        private String solarTerms;
        private String avoid;
        private String lunarCalendar;
        private String suit;
        private int dayOfYear;
        private int weekOfYear;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getWeekDay() {
            return weekDay;
        }

        public void setWeekDay(int weekDay) {
            this.weekDay = weekDay;
        }

        public String getYearTips() {
            return yearTips;
        }

        public void setYearTips(String yearTips) {
            this.yearTips = yearTips;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTypeDes() {
            return typeDes;
        }

        public void setTypeDes(String typeDes) {
            this.typeDes = typeDes;
        }

        public String getChineseZodiac() {
            return chineseZodiac;
        }

        public void setChineseZodiac(String chineseZodiac) {
            this.chineseZodiac = chineseZodiac;
        }

        public String getSolarTerms() {
            return solarTerms;
        }

        public void setSolarTerms(String solarTerms) {
            this.solarTerms = solarTerms;
        }

        public String getAvoid() {
            return avoid;
        }

        public void setAvoid(String avoid) {
            this.avoid = avoid;
        }

        public String getLunarCalendar() {
            return lunarCalendar;
        }

        public void setLunarCalendar(String lunarCalendar) {
            this.lunarCalendar = lunarCalendar;
        }

        public String getSuit() {
            return suit;
        }

        public void setSuit(String suit) {
            this.suit = suit;
        }

        public int getDayOfYear() {
            return dayOfYear;
        }

        public void setDayOfYear(int dayOfYear) {
            this.dayOfYear = dayOfYear;
        }

        public int getWeekOfYear() {
            return weekOfYear;
        }

        public void setWeekOfYear(int weekOfYear) {
            this.weekOfYear = weekOfYear;
        }

        public String getWeekDayCN() {
            switch (weekDay) {
                case 1:
                    return "星期一";
                case 2:
                    return "星期二";
                case 3:
                    return "星期三";
                case 4:
                    return "星期四";
                case 5:
                    return "星期五";
                case 6:
                    return "星期六";
                case 7:
                    return "星期日";
                default:
                    return "";
            }
        }
    }
}
