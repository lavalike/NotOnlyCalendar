package com.notonly.calendar.base.retrofit.interceptor;

import com.notonly.calendar.base.manager.APIManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * RequestInterceptor
 * Created by wangzhen on 2020/6/11.
 */
public class RequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (APIManager.getBaseUrl().contains(request.url().host())) {
            request = request.newBuilder()
                    .addHeader("app_id", APIManager.APP_ID)
                    .addHeader("app_secret", APIManager.APP_SECRET)
                    .build();
        }
        return chain.proceed(request);
    }
}
