package com.notonly.calendar.domain.enums;

import android.Manifest;
import android.text.TextUtils;

/**
 * dangerous 高危权限列表
 *
 * @date 2016/9/19 10:08.
 */
public enum Permission {
    /**
     * 危险权限：
     * group:android.permission-group.CONTACTS
     * permission:android.permission.WRITE_CONTACTS
     * permission:android.permission.GET_ACCOUNTS
     * permission:android.permission.READ_CONTACTS
     * <p/>
     * group:android.permission-group.PHONE
     * permission:android.permission.READ_CALL_LOG
     * permission:android.permission.READ_PHONE_STATE
     * permission:android.permission.CALL_PHONE
     * permission:android.permission.WRITE_CALL_LOG
     * permission:android.permission.USE_SIP
     * permission:android.permission.PROCESS_OUTGOING_CALLS
     * permission:com.android.voicemail.permission.ADD_VOICEMAIL
     * <p/>
     * group:android.permission-group.CALENDAR
     * permission:android.permission.READ_CALENDAR
     * permission:android.permission.WRITE_CALENDAR
     * <p/>
     * group:android.permission-group.CAMERA
     * permission:android.permission.CAMERA
     * <p/>
     * group:android.permission-group.SENSORS
     * permission:android.permission.BODY_SENSORS
     * <p/>
     * group:android.permission-group.LOCATION
     * permission:android.permission.ACCESS_FINE_LOCATION
     * permission:android.permission.ACCESS_COARSE_LOCATION
     * <p/>
     * group:android.permission-group.STORAGE
     * permission:android.permission.READ_EXTERNAL_STORAGE
     * permission:android.permission.WRITE_EXTERNAL_STORAGE
     * <p/>
     * group:android.permission-group.MICROPHONE
     * permission:android.permission.RECORD_AUDIO
     * <p/>
     * group:android.permission-group.SMS
     * permission:android.permission.READ_SMS
     * permission:android.permission.RECEIVE_WAP_PUSH
     * permission:android.permission.RECEIVE_MMS
     * permission:android.permission.RECEIVE_SMS
     * permission:android.permission.SEND_SMS
     * permission:android.permission.READ_CELL_BROADCASTS
     */

    // 存储 - 权限组
    STORAGE_WRITE(Manifest.permission.WRITE_EXTERNAL_STORAGE),
    STORAGE_READE(Manifest.permission.READ_EXTERNAL_STORAGE),

    // 联系人 - 权限组
    CONTACTS_WRITE(Manifest.permission.WRITE_CONTACTS),
    CONTACTS_GET(Manifest.permission.GET_ACCOUNTS),
    CONTACTS_READE(Manifest.permission.READ_CONTACTS),

    // 电话 - 权限组
    PHONE_READE_CALL_LOG(Manifest.permission.READ_CALL_LOG),
    PHONE_READ_PHONE_STATE(Manifest.permission.READ_PHONE_STATE),
    PHONE_CALL_PHONE(Manifest.permission.CALL_PHONE),
    PHONE_WRITE_CALL_LOG(Manifest.permission.WRITE_CALL_LOG),
    PHONE_USE_SIP(Manifest.permission.USE_SIP),
    PHONE_PROCESS_OUTGOING_CALLS(Manifest.permission.PROCESS_OUTGOING_CALLS),
    PHONE_ADD_VOICEMAIL(Manifest.permission.ADD_VOICEMAIL),

    // 日历 - 权限组
    CALENDAR_WRITE(Manifest.permission.WRITE_CALENDAR),
    CALENDAR_READE(Manifest.permission.READ_CALENDAR),

    // 传感器 - 权限组
    SENSORS_BODY(Manifest.permission.BODY_SENSORS),

    // 相机 - 权限组
    CAMERA(Manifest.permission.CAMERA),

    // 位置 - 权限组
    LOCATION_COARSE(Manifest.permission.ACCESS_COARSE_LOCATION),
    LOCATION_FINE(Manifest.permission.ACCESS_FINE_LOCATION),

    // 话筒/麦克风 - 权限组
    MICROPHONE_RECORD_AUDIO(Manifest.permission.RECORD_AUDIO),

    // 短信 - 权限组
    SMS_READ(Manifest.permission.READ_SMS),
    SMS_RECEIVE_WAP_PUSH(Manifest.permission.RECEIVE_WAP_PUSH),
    SMS_RECEIVE_MMS(Manifest.permission.RECEIVE_MMS),
    SMS_RECEIVE(Manifest.permission.RECEIVE_SMS),
    SMS_SEND(Manifest.permission.SEND_SMS),
    SMS_READ_CELL_BROADCASTS("permission:android.permission.READ_CELL_BROADCASTS");

    private String mPermission;

    Permission(String permission) {
        this.mPermission = permission;
    }

    public String getPermission() {
        return mPermission;
    }

    /**
     * 是否为指定权限
     *
     * @param permission 指定权限
     * @return true / false
     */
    public boolean isPermission(String permission) {
        if (TextUtils.isEmpty(permission))
            return false;

        return mPermission.equals(permission);
    }
}
