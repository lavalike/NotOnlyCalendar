package com.notonly.calendar.ui.view

import android.content.Intent
import android.os.Bundle
import com.notonly.calendar.R
import com.notonly.calendar.base.BaseActivity
import com.notonly.calendar.base.toolbar.AppCommonToolbar
import com.notonly.calendar.databinding.ActivitySettingBinding
import com.wangzhen.commons.toolbar.impl.Toolbar

/**
 * SettingActivity
 * Created by wangzhen on 2020/9/4.
 */
class SettingActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySettingBinding.inflate(layoutInflater).apply {
            binding = this
        }.root)

        initViews()
    }

    private fun initViews() {
        with(binding) {
            containerAbout.setOnClickListener {
                startActivity(Intent(this@SettingActivity, AboutActivity::class.java))
            }
            containerPrivacy.setOnClickListener {
                startActivity(
                    Intent(
                        this@SettingActivity,
                        BrowserActivity::class.java
                    ).putExtra("url", "https://www.jianshu.com/p/58f96dd3a365")
                        .putExtra("title", "隐私政策")
                )
            }
        }
    }

    override fun createToolbar(): Toolbar {
        return AppCommonToolbar(this, getString(R.string.title_setting))
    }
}