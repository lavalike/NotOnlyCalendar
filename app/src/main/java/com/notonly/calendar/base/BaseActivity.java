package com.notonly.calendar.base;

import android.content.Context;
import android.os.Bundle;

import com.notonly.calendar.base.manager.AppManager;
import com.umeng.analytics.MobclickAgent;
import com.wangzhen.commons.toolbar.ToolbarActivity;

/**
 * Activity基类
 * Created by wangzhen on 16/2/23.
 */
public class BaseActivity extends ToolbarActivity {

    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.get().addActivity(this);
        mContext = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppManager.get().removeActivity(this);
    }

}
