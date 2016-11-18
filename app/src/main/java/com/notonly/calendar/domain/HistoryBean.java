package com.notonly.calendar.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 历史上的数据 实体类
 * Created by wangzhen on 16/1/13.
 */
public class HistoryBean extends BaseBean {

    /**
     * result : [{"day":"11/18","date":"前700年11月18日","title":"卫宣公病逝","e_id":"12912"},{"day":"11/18","date":"1067年11月18日","title":"司马光初进读通志，赐名《资治通鉴》","e_id":"12913"},{"day":"11/18","date":"1095年11月18日","title":"克勒芒会议召开，罗马教皇发起十字军东征","e_id":"12914"},{"day":"11/18","date":"1155年11月18日","title":"宋朝著名奸臣秦桧去世","e_id":"12915"},{"day":"11/18","date":"1787年11月18日","title":"照相机的发明者达盖尔出生","e_id":"12916"},{"day":"11/18","date":"1836年11月18日","title":"清北洋水师提督丁汝昌出生","e_id":"12917"},{"day":"11/18","date":"1899年11月18日","title":"杰出的工人运动领袖李立三诞辰","e_id":"12918"},{"day":"11/18","date":"1901年11月18日","title":"英国同意将运河权交给美国","e_id":"12919"},{"day":"11/18","date":"1915年11月18日","title":"蔡锷脱袁离津南下","e_id":"12920"},{"day":"11/18","date":"1918年11月18日","title":"拉脱维亚宣告独立","e_id":"12921"},{"day":"11/18","date":"1928年11月18日","title":"\u201c米老鼠\u201d诞生","e_id":"12922"},{"day":"11/18","date":"1928年11月18日","title":"沃尔特·迪斯尼和他的动画世界","e_id":"12923"},{"day":"11/18","date":"1958年11月18日","title":"海河拦河大坝合龙","e_id":"12924"},{"day":"11/18","date":"1962年11月18日","title":"对原子弹作出贡献的玻尔去世","e_id":"12925"},{"day":"11/18","date":"1963年11月18日","title":"欧阳海牺牲","e_id":"12926"},{"day":"11/18","date":"1963年11月18日","title":"伊拉克发生新的军事政变","e_id":"12927"},{"day":"11/18","date":"1965年11月18日","title":"林彪提出\u201c突出政治\u201d五原则","e_id":"12928"},{"day":"11/18","date":"1974年11月18日","title":"美国总统福特首次访日本","e_id":"12929"},{"day":"11/18","date":"1978年11月18日","title":"美国\u201c人民圣殿教\u201d900多信徒集体自杀","e_id":"12930"},{"day":"11/18","date":"1981年11月18日","title":"美国总统里根提出\u201c零点方案\u201d","e_id":"12931"},{"day":"11/18","date":"1981年11月18日","title":"费孝通荣获赫胥黎奖章","e_id":"12932"},{"day":"11/18","date":"1982年11月18日","title":"日美地面部队首次联合实战演习","e_id":"12933"},{"day":"11/18","date":"1983年11月18日","title":"我国强烈抗议美国的\u201c台湾前途\u201d决议案","e_id":"12934"},{"day":"11/18","date":"1983年11月18日","title":"美苏两艘驱逐舰在海湾相撞","e_id":"12935"},{"day":"11/18","date":"1984年11月18日","title":"飞乐音响公司发行中国第一批股票","e_id":"12936"},{"day":"11/18","date":"1986年11月18日","title":"伦敦地铁发生火灾 造成32人死亡 100多人受伤","e_id":"12937"},{"day":"11/18","date":"1986年11月18日","title":"《\u201c八六三\u201d计划纲要》发布","e_id":"12938"},{"day":"11/18","date":"1987年11月18日","title":"移动电话网\u201c大哥大\u201d开通使用","e_id":"12939"},{"day":"11/18","date":"1988年11月18日","title":"我国电影艺术家塞克逝世","e_id":"12940"},{"day":"11/18","date":"1995年11月18日","title":"丹麦王子迎娶香港姑娘","e_id":"12941"},{"day":"11/18","date":"1996年11月18日","title":"俄飞船发射失败　损失惨重","e_id":"12942"},{"day":"11/18","date":"1996年11月18日","title":"NBA状告CNBA侵犯其商标专用权","e_id":"12943"},{"day":"11/18","date":"1996年11月18日","title":"英法海峡隧道发生火灾","e_id":"12944"},{"day":"11/18","date":"1998年11月18日","title":"中国保险监督管理委员会成立","e_id":"12945"},{"day":"11/18","date":"1998年11月18日","title":"韩国首批游客赴朝鲜观光","e_id":"12946"},{"day":"11/18","date":"1998年11月18日","title":"我国第一家跨行政区银行挂牌","e_id":"12947"},{"day":"11/18","date":"1998年11月18日","title":"狮子座流星雨","e_id":"12948"},{"day":"11/18","date":"1999年11月18日","title":"日本针对奥姆教通过相关法案","e_id":"12949"},{"day":"11/18","date":"2001年11月18日","title":"我科学家在体外造出胃肠粘膜组织","e_id":"12950"},{"day":"11/18","date":"2015年11月18日","title":"证监会稽查总队习龙生被带走","e_id":"12951"}]
     */

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * day : 11/18
         * date : 前700年11月18日
         * title : 卫宣公病逝
         * e_id : 12912
         */

        private String day;
        private String date;
        private String title;
        private String e_id;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getE_id() {
            return e_id;
        }

        public void setE_id(String e_id) {
            this.e_id = e_id;
        }
    }
}
