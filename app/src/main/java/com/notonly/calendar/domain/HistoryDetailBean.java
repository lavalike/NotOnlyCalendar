package com.notonly.calendar.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 历史上的今天详情
 * Created by wangzhen on 16/5/6.
 */
public class HistoryDetailBean extends BaseBean {

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * e_id : 12912
         * title : 卫宣公病逝
         * content :     在2716年前的今天，前700年11月18日 (农历腊月初五)，卫宣公病逝。
         * 公元前700年11月18日（距今已2716多年）卫宣公病逝。
         * 卫宣公为人淫纵不检。作公子的时候就与其父卫庄公的妾夷姜私通，生下了长子公子急，寄养于民间。登基后依然淫性不减，因原配邢妃不受宠，就立了公子急为嗣子。公子急十六岁时，聘了齐僖公的女儿宣姜为妻，卫宣公因为听说宣姜美貌，就自己迎娶了宣姜。后来宣姜有为卫宣公生了公子寿和公子朔两个儿子。卫宣公因为宠爱宣姜就想立公子寿而废公子急。但是因为公子寿和公子急兄弟情深，就没能公开进行废立之事。后来，宣姜与怀有野心的公子朔设计要加害公子急，计划以出使齐国之名让公子急离开都城，然后在半路重金买通盗贼暗杀公子急。结果被公子寿事先发觉告诉了公子急。但公子急却执意要杀身成仁，情急无奈之下，公子寿就以送别为名设酒席灌醉了公子急，而自己冒充公子急出使齐国，结果在半路被盗贼暗杀。酒醒后的公子急急忙赶到亮明了身份也被盗贼杀害。《诗经，卫风》的《乘舟》就记载了这件事。丧子后的卫宣公精神恍惚，不久就病死了。
         * 一位贪色之君主。
         * <p>
         * <p>
         * picNo : 2
         * picUrl : [{"pic_title":"","id":1,"url":"http://images.juheapi.com/history/12912_1.jpg"},{"pic_title":"宣姜者，齐僖公长女，国色天姿。卫宣公为子求婚于齐，却贪宣姜美色，筑新台以藏娇","id":2,"url":"http://images.juheapi.com/history/12912_2.jpg"}]
         */

        private String e_id;
        private String title;
        private String content;
        private String picNo;
        private List<PicUrlBean> picUrl;

        public String getE_id() {
            return e_id;
        }

        public void setE_id(String e_id) {
            this.e_id = e_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPicNo() {
            return picNo;
        }

        public void setPicNo(String picNo) {
            this.picNo = picNo;
        }

        public List<PicUrlBean> getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(List<PicUrlBean> picUrl) {
            this.picUrl = picUrl;
        }

        public static class PicUrlBean implements Serializable {
            /**
             * pic_title :
             * id : 1
             * url : http://images.juheapi.com/history/12912_1.jpg
             */

            private String pic_title;
            private int id;
            private String url;

            public String getPic_title() {
                return pic_title;
            }

            public void setPic_title(String pic_title) {
                this.pic_title = pic_title;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
