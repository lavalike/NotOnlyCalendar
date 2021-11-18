package com.notonly.calendar.domain

import java.io.Serializable

/**
 * 实体类公共部分
 * Created by wangzhen on 2016/11/18.
 */
open class BaseBean : Serializable {
    var reason: String? = null
    var error_code = 0
}