package com.notonly.calendar.base.manager

import android.Manifest
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.notonly.calendar.R
import com.notonly.calendar.util.T
import com.pgyersdk.update.PgyUpdateManager
import com.pgyersdk.update.UpdateManagerListener
import com.pgyersdk.update.javabean.AppBean
import com.wangzhen.commons.utils.AppUtils
import com.wangzhen.permission.PermissionManager
import com.wangzhen.permission.callback.AbsPermissionCallback

/**
 * 更新检测
 * Created by wangzhen on 2017/3/8.
 */
class UpdateManager private constructor(val activity: FragmentActivity) {
    fun check() {
        PgyUpdateManager.register(object : UpdateManagerListener {
            override fun onNoUpdateAvailable() {}
            override fun onUpdateAvailable(appBean: AppBean?) {
                if (appBean != null) {
                    val currentCode = AppUtils.getVersionCode().toString()
                    val serverCode = appBean.versionCode
                    if (serverCode > currentCode) {
                        val builder = AlertDialog.Builder(activity)
                        builder.setTitle("版本更新 V" + appBean.versionName)
                        builder.setMessage(appBean.releaseNote)
                        builder.setNegativeButton("取消", null)
                        builder.setPositiveButton("更新") { dialog, which ->
                            PermissionManager.request(activity, object : AbsPermissionCallback() {
                                override fun onGrant(permissions: Array<String>) {
                                    download(appBean.downloadURL)
                                }

                                override fun onDeny(
                                    deniedPermissions: Array<String>,
                                    neverAskPermissions: Array<String>
                                ) {
                                    T.get(activity)
                                        .toast(activity.getString(R.string.error_permission_denied))
                                }
                            }, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        }
                        builder.setCancelable(false)
                        builder.show()
                    }
                }
            }

            override fun checkUpdateFailed(e: Exception) {}
        })
    }

    /**
     * 下载更新
     *
     * @param url
     */
    private fun download(url: String) {
        val dialogFragment = DownloadDialogFragment().apply {
            setUrl(url.trim { it <= ' ' })
        }
        activity.supportFragmentManager.beginTransaction().apply {
            add(dialogFragment, DownloadDialogFragment::class.java.simpleName)
            commitAllowingStateLoss()
        }
    }

    companion object {
        operator fun get(activity: FragmentActivity) = UpdateManager(activity)
    }
}