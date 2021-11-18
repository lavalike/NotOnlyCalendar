package com.notonly.calendar.domain

import java.io.Serializable

/**
 * HistoryResponse
 * Created by wangzhen on 2020/6/13.
 */
class HistoryResponse : BaseBean() {
    var code = 0
    var msg: String? = null
    var data: List<DataBean>? = null
    val isSuccess: Boolean
        get() = code == 1

    class DataBean : Serializable {
        var picUrl: String? = null
        var title: String? = null
        var year: String? = null
        var month: String? = null
        var day: String? = null
        var details: String? = null
    }
}