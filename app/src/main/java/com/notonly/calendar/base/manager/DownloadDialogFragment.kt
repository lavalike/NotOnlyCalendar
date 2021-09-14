package com.notonly.calendar.base.manager

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.notonly.calendar.R
import com.notonly.calendar.base.Constants
import com.notonly.calendar.databinding.DialogProgressDownloadLayoutBinding
import com.notonly.calendar.util.DownloadUtil
import com.notonly.calendar.util.T
import java.io.File

/**
 * 显示进度的文件下载对话框
 * Created by wangzhen on 2017/6/30.
 */
class DownloadDialogFragment : DialogFragment() {
    private lateinit var binding: DialogProgressDownloadLayoutBinding
    private var url: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_progress_download_layout, container, false)
        binding = DialogProgressDownloadLayoutBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        download()
    }

    private fun download() {
        DownloadUtil.get()
            .setListener(object : DownloadUtil.OnDownloadListener {
                override fun onLoading(progress: Int) {
                    updateMessage(progress)
                }

                override fun onSuccess(path: String) {
                    if (!path.endsWith(".apk")) {
                        T.get(context).toast(getString(R.string.error_invalid_apk_file))
                        dismissAllowingStateLoss()
                        return
                    }
                    val file = File(path)
                    if (file.exists()) {
                        binding.tvTitle.text = getString(R.string.tip_update_complete)
                        Handler(Looper.getMainLooper()).postDelayed({
                            context?.let { ctx ->
                                val install = Intent(Intent.ACTION_VIEW)
                                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                //兼容7.0私有文件权限
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    val apkUri = FileProvider.getUriForFile(
                                        ctx,
                                        ctx.packageName + ".fileProvider",
                                        file
                                    )
                                    install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                    install.setDataAndType(apkUri, Constants.MIME_APK)
                                } else {
                                    install.setDataAndType(Uri.fromFile(file), Constants.MIME_APK)
                                }
                                ctx.startActivity(install)
                            }
                            dismissAllowingStateLoss()
                        }, 1000)
                    }
                }

                override fun onFail(err: String) {
                    T.get(context).toast(err)
                    dismissAllowingStateLoss()
                }
            })
            .setFileName("NotOnlyCalendar.apk")
            .download(url)
    }

    fun setTitle(title: String?) {
        if (!TextUtils.isEmpty(title)) binding.tvTitle.text = title
    }

    /**
     * 更新进度信息
     *
     * @param progress 进度
     */
    private fun updateMessage(progress: Int) {
        var value = progress
        if (value < 0) value = 0
        if (value > 100) value = 100
        binding.progressBar.progress = value
        binding.tvProgress.text = value.toString()
    }

    /**
     * 设置下载url
     *
     * @param url
     */
    fun setUrl(url: String?) {
        this.url = url
    }

    override fun onStart() {
        super.onStart()
        initWindow()
    }

    private fun initWindow() {
        if (dialog == null) return
        val window = dialog!!.window
        if (window != null) {
            val attr: WindowManager.LayoutParams = window.attributes
            attr.width = WindowManager.LayoutParams.MATCH_PARENT
            window.attributes = attr
        }
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setCancelable(false)
    }
}