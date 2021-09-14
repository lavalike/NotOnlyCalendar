package com.notonly.calendar.network

import com.notonly.calendar.base.manager.APIManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * RequestInterceptor
 * Created by wangzhen on 2020/6/11.
 */
class RequestInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (APIManager.baseUrl.contains(request.url.host)) {
            request = request.newBuilder()
                .addHeader("app_id", APIManager.APP_ID)
                .addHeader("app_secret", APIManager.APP_SECRET)
                .build()
        }
        return chain.proceed(request)
    }
}