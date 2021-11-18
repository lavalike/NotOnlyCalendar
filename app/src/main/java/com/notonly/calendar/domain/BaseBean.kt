package com.notonly.calendar.domain;

import java.io.Serializable;

/**
 * 实体类公共部分
 * Created by wangzhen on 2016/11/18.
 */

public class BaseBean implements Serializable {
    private String reason;
    private int error_code;

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
}
