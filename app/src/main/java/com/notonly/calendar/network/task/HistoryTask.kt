package com.notonly.calendar.network.task

import com.wangzhen.network.callback.RequestCallback
import com.wangzhen.network.task.GetTask

/**
 * HistoryTask
 * Created by wangzhen on 2020/6/13.
 */
class HistoryTask(callback: RequestCallback<*>) : GetTask(callback) {
    override fun onSetupParams(vararg params: Any) {
        put("type", params[0]) //type：是否需要详情，0：不需要详情 1：需要详情 默认值 0 可不传
    }

    override fun getApi(): String {
        return "/history/today"
    }

    companion object {
        const val TYPE_NO_DETAILS = 0
        const val TYPE_NEED_DETAILS = 1
    }
}