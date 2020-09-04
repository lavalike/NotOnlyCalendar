package com.notonly.calendar.ui.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.notonly.calendar.R;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.base.toolbar.ToolBarCommonHolder;

/**
 * BrowserActivity
 * Created by wangzhen on 2020/9/4.
 */
public class BrowserActivity extends BaseActivity {

    private WebView mWebView;
    private String mTitle;
    private ToolBarCommonHolder mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        mWebView = findViewById(R.id.web);
        config();
        mWebView.loadUrl(getIntent().getStringExtra("url"));
    }

    private void config() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http")) {
                    view.loadUrl(url);
                }
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (TextUtils.isEmpty(mTitle)) {
                    mToolbar.setTitle(title);
                }
            }
        });
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        mTitle = getIntent().getStringExtra("title");
        mToolbar = new ToolBarCommonHolder(this, toolbar, mTitle);
    }
}