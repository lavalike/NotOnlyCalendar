package com.notonly.calendar.base.retrofit.progress;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 自定义ProgressInterceptor(进度过滤器)
 * Created by wangzhen on 2017/4/5.
 */
public class ProgressInterceptor implements Interceptor {

    /**
     * 进度回调监听
     */
    private ProgressListener progressListener;

    public ProgressInterceptor(ProgressListener listener) {
        this.progressListener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //拦截Request
        Request request = chain.request();

        if (request.body() != null) {
            // 包装请求体
            request = request.newBuilder()
                    .post(new ProgressRequestBody(request.body(), progressListener))
                    .build();
        }

        //拦截Response
        Response response = chain.proceed(request);

        if (response.body() != null) {
            //包装响应体并返回
            response = response.newBuilder()
                    .body(new ProgressResponseBody(response.body(), progressListener))
                    .build();
        }

        return response;
    }
}
