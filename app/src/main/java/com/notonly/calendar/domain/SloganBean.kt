package com.notonly.calendar.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 首页欢迎语
 * Created by wangzhen on 2017/4/24.
 */
public class SloganBean extends BaseBean {

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

    private String sid;
    private String tts;
    private String content;
    private String note;
    private String love;
    private String translation;
    private String picture;
    private String picture2;
    private String caption;
    private String dateline;
    private String s_pv;
    private String sp_pv;
    private String fenxiang_img;
    private List<TagsBean> tags;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTts() {
        return tts;
    }

    public void setTts(String tts) {
        this.tts = tts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getS_pv() {
        return s_pv;
    }

    public void setS_pv(String s_pv) {
        this.s_pv = s_pv;
    }

    public String getSp_pv() {
        return sp_pv;
    }

    public void setSp_pv(String sp_pv) {
        this.sp_pv = sp_pv;
    }

    public String getFenxiang_img() {
        return fenxiang_img;
    }

    public void setFenxiang_img(String fenxiang_img) {
        this.fenxiang_img = fenxiang_img;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public static class TagsBean implements Serializable {
        /**
         * id : null
         * name : null
         */

        private Object id;
        private Object name;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }
    }
}
