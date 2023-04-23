package com.notonly.calendar.ui.view

import android.os.Bundle
import com.notonly.calendar.R
import com.notonly.calendar.base.BaseActivity
import com.notonly.calendar.base.toolbar.AppCommonToolbar
import com.notonly.calendar.databinding.ActivityAboutBinding
import com.notonly.calendar.util.WxUtils
import com.wangzhen.commons.toolbar.impl.Toolbar
import com.wangzhen.utils.utils.AppUtils

/**
 * 关于页面
 * created by wangzhen on 2016/11/18
 */
class AboutActivity : BaseActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityAboutBinding.inflate(layoutInflater).apply {
            binding = this
        }.root)

        binding.tvVersion.text = String.format("版本：%s", AppUtils.getVersionName())
        binding.btnJump.setOnClickListener {
            WxUtils.openMiniProgram(this)
        }
    }

    override fun createToolbar(): Toolbar {
        return AppCommonToolbar(this, getString(R.string.title_about))
    }
}