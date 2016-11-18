package com.notonly.calendar.domain;

import cn.bmob.v3.BmobObject;

/**
 * 设备信息
 * Created by wangzhen on 16/1/13.
 */
public class Device extends BmobObject {

    private String DeviceID;
    private String AppVersion;
    private String SysVersion;
    private String Brand;
    private String Model;
    private String MobileNumber;

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getSysVersion() {
        return SysVersion;
    }

    public void setSysVersion(String sysVersion) {
        SysVersion = sysVersion;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }
}
