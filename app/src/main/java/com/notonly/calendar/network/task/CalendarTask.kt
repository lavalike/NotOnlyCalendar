package com.notonly.calendar.network.task

import com.wangzhen.network.callback.RequestCallback
import com.wangzhen.network.task.GetTask

/**
 * CalendarTask
 * Created by wangzhen on 2020/6/13.
 */
class CalendarTask(callback: RequestCallback<*>) : GetTask(callback) {
    private var date: String? = null
    override fun onSetupParams(vararg params: Any) {}
    fun setDate(date: String?): CalendarTask {
        this.date = date
        return this
    }

    override fun getApi(): String {
        return "/holiday/single/$date"
    }
}