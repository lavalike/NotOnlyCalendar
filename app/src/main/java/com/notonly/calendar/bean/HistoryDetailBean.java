package com.notonly.calendar.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 历史上的今天详情
 * Created by wangzhen on 16/5/6.
 */
public class HistoryDetailBean implements Serializable {

    /**
     * result : [{"_id":"19051102","title":"日本颁布取缔中国留学生规则","pic":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/200411/2/2A182255941.jpg","year":1905,"month":11,"day":2,"des":"在111年前的今天，1905年11月2日 (农历十月初六)，日本颁布取缔中国留学生规则。","content":"在111年前的今天，1905年11月2日 (农历十月初六)，日本颁布取缔中国留学生规则。\n在日本的中国学生\n1905年11月2日，日本文部省徇清廷要求，颁布《关于清国人入学之公私立学校之规则》。中国留日学生到1905年已增至8000人，革命倾向日趋强烈。日本政府应清政府要求力谋加强对中国留学生的管束。4月，文部省就曾训令各有关学校校长，声称清国人在本邦留学者愈来愈多，其中可能有人议论本国政治，举动不当，担任教养清国留学生之职者必须深刻注意此点，使彼等不失学生本分。《规则》颁布后，激起中国留学生的强烈反对，一场轰轰烈烈的斗争随之掀起。11月27日，留日学生决议上书清政府驻日公使杨枢，详细罗列理由，要求取消《规则》中的第九、十两条。\n","lunar":"乙巳年十月初六"}]
     * reason : 请求成功！
     * error_code : 0
     */

    private String reason;
    private int error_code;
    /**
     * _id : 19051102
     * title : 日本颁布取缔中国留学生规则
     * pic : http://juheimg.oss-cn-hangzhou.aliyuncs.com/toh/200411/2/2A182255941.jpg
     * year : 1905
     * month : 11
     * day : 2
     * des : 在111年前的今天，1905年11月2日 (农历十月初六)，日本颁布取缔中国留学生规则。
     * content : 在111年前的今天，1905年11月2日 (农历十月初六)，日本颁布取缔中国留学生规则。
     * 在日本的中国学生
     * 1905年11月2日，日本文部省徇清廷要求，颁布《关于清国人入学之公私立学校之规则》。中国留日学生到1905年已增至8000人，革命倾向日趋强烈。日本政府应清政府要求力谋加强对中国留学生的管束。4月，文部省就曾训令各有关学校校长，声称清国人在本邦留学者愈来愈多，其中可能有人议论本国政治，举动不当，担任教养清国留学生之职者必须深刻注意此点，使彼等不失学生本分。《规则》颁布后，激起中国留学生的强烈反对，一场轰轰烈烈的斗争随之掀起。11月27日，留日学生决议上书清政府驻日公使杨枢，详细罗列理由，要求取消《规则》中的第九、十两条。
     * <p>
     * lunar : 乙巳年十月初六
     */

    private List<ResultBean> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        private String _id;
        private String title;
        private String pic;
        private int year;
        private int month;
        private int day;
        private String des;
        private String content;
        private String lunar;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLunar() {
            return lunar;
        }

        public void setLunar(String lunar) {
            this.lunar = lunar;
        }
    }
}
