package com.notonly.calendar.domain;

import java.io.Serializable;
import java.util.List;

/**
 * HistoryResponse
 * Created by wangzhen on 2020/6/13.
 */
public class HistoryResponse extends BaseBean {
    public int code;
    public String msg;
    public List<DataBean> data;

    public boolean isSuccess() {
        return code == 1;
    }

    public static class DataBean implements Serializable {
        public String picUrl;
        public String title;
        public String year;
        public String month;
        public String day;
        public String details;
    }
}
