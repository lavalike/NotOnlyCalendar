package com.notonly.calendar.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.notonly.calendar.R;
import com.notonly.calendar.base.BaseActivity;
import com.wangzhen.commons.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 启动页
 * wangzhen 2016/04/30 21:09
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.tv_appversion)
    TextView mVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        //隐藏ActionBar
        getSupportActionBar().hide();
        mVersion.setText(String.format("版本：%s", AppUtils.getVersionName()));

        new Handler(Looper.myLooper()).postDelayed(() -> {
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        }, 2000);
    }
}
