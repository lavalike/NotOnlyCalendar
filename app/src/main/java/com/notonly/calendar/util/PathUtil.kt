package com.notonly.calendar.util

import com.wangzhen.commons.utils.AppUtils
import java.io.File

/**
 * 路径工具类
 * Created by wangzhen on 2016/11/18.
 */
object PathUtil {
    //图片路径
    private const val PATH_IMAGE = "images"

    //文件下载路径
    private const val PATH_DOWNLOAD = "download"

    @JvmStatic
    val imagePath: String
        get() {
            val path = File(AppUtils.getApplication().getExternalFilesDir(null), PATH_IMAGE)
            if (!path.exists()) {
                val ignore = path.mkdirs()
            }
            return path.absolutePath
        }

    @JvmStatic
    val downloadPath: String
        get() {
            val path = File(AppUtils.getApplication().getExternalFilesDir(null), PATH_DOWNLOAD)
            if (!path.exists()) {
                val ignore = path.mkdirs()
            }
            return path.absolutePath
        }
}