package com.notonly.calendar.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.notonly.calendar.base.BaseActivity
import com.notonly.calendar.base.toolbar.AppCommonToolbar
import com.notonly.calendar.databinding.ActivityBrowserBinding
import com.wangzhen.commons.toolbar.impl.Toolbar

/**
 * BrowserActivity
 * Created by wangzhen on 2020/9/4.
 */
class BrowserActivity : BaseActivity() {
    private lateinit var binding: ActivityBrowserBinding
    private lateinit var mToolbar: AppCommonToolbar
    private lateinit var webView: WebView
    private var mTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityBrowserBinding.inflate(layoutInflater).apply {
            binding = this
            webView = binding.web
        }.root)
        config()
        webView.loadUrl(intent.getStringExtra("url")!!)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun config() {
        with(webView) {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    if (url.startsWith("http")) {
                        view.loadUrl(url)
                    }
                    return true
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView, title: String) {
                    super.onReceivedTitle(view, title)
                    if (TextUtils.isEmpty(mTitle)) {
                        mToolbar.setTitle(title)
                    }
                }
            }
        }
    }

    override fun createToolbar(): Toolbar {
        mTitle = intent.getStringExtra("title")
        mToolbar = AppCommonToolbar(this, mTitle)
        return mToolbar
    }
}