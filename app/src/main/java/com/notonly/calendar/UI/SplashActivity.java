package com.notonly.calendar.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.notonly.calendar.R;
import com.notonly.calendar.util.App;
import com.notonly.calendar.util.NetworkUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 启动页
 * wangzhen 2016/04/30 21:09
 */
@ContentView(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity {

    private Context mContext;
    @ViewInject(R.id.tv_appname)
    private TextView mName;
    @ViewInject(R.id.tv_appversion)
    private TextView mVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mContext = this;
        App.getInstance().addActivity(this);
        //隐藏ActionBar
        getSupportActionBar().hide();
        mVersion.setText("版本：" + NetworkUtil.getAppVersion(mContext));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
            }
        }, 3000);
    }
}
