package com.notonly.calendar.base

import android.os.Bundle
import com.notonly.calendar.base.manager.AppManager
import com.umeng.analytics.MobclickAgent
import com.wangzhen.commons.toolbar.ToolbarActivity

/**
 * Activity基类
 * Created by wangzhen on 16/2/23.
 */
open class BaseActivity : ToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.get().addActivity(this)
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    public override fun onDestroy() {
        super.onDestroy()
        AppManager.get().removeActivity(this)
    }
}