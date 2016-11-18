package com.notonly.calendar.base.helper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.notonly.calendar.base.manager.AppManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Set;


/**
 * 轻量级数据存储助手
 *
 * @author wangzhen
 * @date 2016-10-20 下午19:09:00
 */
public class SPHelper {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SPHelper() {
        sharedPreferences = AppManager.get().getActivity().getSharedPreferences(
                AppManager.get().getAppName(), Activity.MODE_PRIVATE);
    }

    public SPHelper(String spName) {
        sharedPreferences = AppManager.get().getActivity().getSharedPreferences(spName,
                Activity.MODE_PRIVATE);
    }

    /**
     * 获取无参实例
     */
    public static SPHelper getInstance() {
        return new SPHelper();
    }

    /**
     * 获取实例 SharedPreferences的name为spName
     */
    public static SPHelper getInstance(String spName) {
        return new SPHelper(spName);
    }

    public SharedPreferences.Editor getSharedPreferencesEditor() {
        return sharedPreferences.edit();
    }

    /**
     * 保存数据
     *
     * @param key   关键字
     * @param value 值
     * @param <T>   泛型可为：int、float、boolean、String、long、Set<String>
     * @return
     */
    public <T> SPHelper put(String key, T value) {
        if (editor == null) {
            editor = getSharedPreferencesEditor();
        }
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);

        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);

        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);

        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);

        } else if (value instanceof String) {
            editor.putString(key, (String) value);

        } else if (value instanceof Set) {
            editor.putStringSet(key, (Set<String>) value);

        } else if (value instanceof Serializable) {
            editor.putString(key, writeObjectToString(value));

        }
        editor.commit();

        return this;
    }

    public String get(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public long get(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public int get(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public float get(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    public boolean get(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public Set<String> get(String key, Set<String> defValue) {
        return sharedPreferences.getStringSet(key, defValue);
    }

    /**
     * 返回:获取保存的bean对象
     *
     * @param key
     * @return modified:
     */
    public <T> T getObject(String key) {
        try {
            if (sharedPreferences.contains(key)) {
                String string = sharedPreferences.getString(key, "");
                if (TextUtils.isEmpty(string)) {
                    return null;
                } else {
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    return (T) is.readObject();
                }
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return null;

    }

    /**
     * 清空数据 Key
     */
    public boolean clear() {
        if (editor == null) {
            editor = getSharedPreferencesEditor();
        }
        return editor.clear().commit();
    }

    /**
     * 返回: 对象字节流
     *
     * @param data 保存的对象string
     * @return modified:
     */
    private byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;
            char hex_char1 = hexString.charAt(i);
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i);
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48);
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55;
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;
        }
        return retData;
    }


    /**
     * 返回:将序列化对象保存到sharepreferener
     *
     * @param obj
     * @return modified:
     */
    private String writeObjectToString(Object obj) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            os.writeObject(obj);
            return (bytesToHexString(bos.toByteArray()).equals("") ||
                    bytesToHexString(bos.toByteArray()) == null) ? null : bytesToHexString(bos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回:将数组转为16进制
     *
     * @param bArray
     * @return modified:
     */
    private String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

}
