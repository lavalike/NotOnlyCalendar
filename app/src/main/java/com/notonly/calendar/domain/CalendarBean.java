package com.notonly.calendar.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by wangzhen on 16/1/12.
 */
public class CalendarBean {

    /**
     * reason : Success
     * result : {"data":{"avoid":"开市.嫁娶.移徙.入宅.掘井.安葬.","animalsYear":"鸡","weekday":"星期一","suit":"沐浴.斋醮.解除.求医.治病.会亲友.造畜椆栖.栽种.理发.扫舍.","lunarYear":"丁酉年","lunar":"三月廿八","year-month":"2017-4","date":"2017-4-24"}}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean implements Serializable {
        /**
         * data : {"avoid":"开市.嫁娶.移徙.入宅.掘井.安葬.","animalsYear":"鸡","weekday":"星期一","suit":"沐浴.斋醮.解除.求医.治病.会亲友.造畜椆栖.栽种.理发.扫舍.","lunarYear":"丁酉年","lunar":"三月廿八","year-month":"2017-4","date":"2017-4-24"}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * avoid : 开市.嫁娶.移徙.入宅.掘井.安葬.
             * animalsYear : 鸡
             * weekday : 星期一
             * suit : 沐浴.斋醮.解除.求医.治病.会亲友.造畜椆栖.栽种.理发.扫舍.
             * lunarYear : 丁酉年
             * lunar : 三月廿八
             * year-month : 2017-4
             * date : 2017-4-24
             */

            private String avoid;
            private String animalsYear;
            private String weekday;
            private String suit;
            private String lunarYear;
            private String lunar;
            @SerializedName("year-month")
            private String yearmonth;
            private String date;

            public String getAvoid() {
                return avoid;
            }

            public void setAvoid(String avoid) {
                this.avoid = avoid;
            }

            public String getAnimalsYear() {
                return animalsYear;
            }

            public void setAnimalsYear(String animalsYear) {
                this.animalsYear = animalsYear;
            }

            public String getWeekday() {
                return weekday;
            }

            public void setWeekday(String weekday) {
                this.weekday = weekday;
            }

            public String getSuit() {
                return suit;
            }

            public void setSuit(String suit) {
                this.suit = suit;
            }

            public String getLunarYear() {
                return lunarYear;
            }

            public void setLunarYear(String lunarYear) {
                this.lunarYear = lunarYear;
            }

            public String getLunar() {
                return lunar;
            }

            public void setLunar(String lunar) {
                this.lunar = lunar;
            }

            public String getYearmonth() {
                return yearmonth;
            }

            public void setYearmonth(String yearmonth) {
                this.yearmonth = yearmonth;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }
}
