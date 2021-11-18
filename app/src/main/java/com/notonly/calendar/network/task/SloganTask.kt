package com.notonly.calendar.network.task

import com.wangzhen.network.callback.RequestCallback
import com.wangzhen.network.task.GetTask
import com.notonly.calendar.base.manager.APIManager

/**
 * SloganTask
 * Created by wangzhen on 2020/6/13.
 */
class SloganTask(callback: RequestCallback<*>) : GetTask(callback) {
    override fun onSetupParams(vararg params: Any) {}
    override fun getApi(): String {
        return APIManager.URL_SLOGAN
    }
}