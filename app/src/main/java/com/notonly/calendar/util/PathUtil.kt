package com.notonly.calendar.util;

import com.wangzhen.commons.utils.AppUtils;

import java.io.File;

/**
 * 路径工具类
 * Created by wangzhen on 2016/11/18.
 */

public class PathUtil {
    //图片路径
    private static final String PATH_IMAGE = "images";
    //文件下载路径
    private static final String PATH_DOWNLOAD = "download";

    public static String getImagePath() {
        File path = new File(AppUtils.getApplication().getExternalFilesDir(null), PATH_IMAGE);
        if (!path.exists()) {
            boolean ignore = path.mkdirs();
        }
        return path.getAbsolutePath();
    }

    public static String getDownloadPath() {
        File path = new File(AppUtils.getApplication().getExternalFilesDir(null), PATH_DOWNLOAD);
        if (!path.exists()) {
            boolean ignore = path.mkdirs();
        }
        return path.getAbsolutePath();
    }
}
