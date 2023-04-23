package com.notonly.calendar.ui.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.notonly.calendar.base.BaseActivity
import com.notonly.calendar.databinding.ActivitySplashBinding
import com.wangzhen.utils.utils.AppUtils

/**
 * 启动页
 * wangzhen 2016/04/30 21:09
 */
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySplashBinding.inflate(layoutInflater).apply {
            binding = this
        }.root)
        supportActionBar?.hide()
        binding.tvAppversion.text = String.format("版本：%s", AppUtils.getVersionName())
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}