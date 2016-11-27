package com.notonly.calendar.UI.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.notonly.calendar.R;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.util.AppUtil;

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
        mVersion.setText("版本：" + AppUtil.getVersionName(mContext));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
            }
        }, 2000);
    }

    @Override
    protected boolean canNavigationBack() {
        return false;
    }
}
