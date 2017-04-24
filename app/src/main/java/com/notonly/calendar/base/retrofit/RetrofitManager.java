package com.notonly.calendar.base.retrofit;

import com.notonly.calendar.base.manager.APIManager;
import com.notonly.calendar.base.retrofit.progress.ProgressInterceptor;
import com.notonly.calendar.base.retrofit.progress.ProgressListener;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 管理类
 * Created by wangzhen on 16/11/9.
 */

public class RetrofitManager {
    /**
     * 获取Retrofit实例
     *
     * @return
     */
    public static Retrofit getClient() {
        return new Retrofit.Builder()
                .baseUrl(APIManager.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
    }

    /**
     * 获取带进度回调的Retrofit实例
     *
     * @param listener
     * @return
     */
    public static Retrofit getProgressClient(ProgressListener listener) {
        return new Retrofit.Builder()
                .baseUrl(APIManager.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                        getOkHttpClient()
                                .newBuilder()
                                .addInterceptor(new ProgressInterceptor(listener))
                                .build()
                )
                .build();
    }

    /**
     * 获取OkHttp实例
     *
     * @return
     */
    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(1000 * 5, TimeUnit.MILLISECONDS);
        builder.readTimeout(1000 * 5, TimeUnit.MILLISECONDS);
        builder.writeTimeout(1000 * 5, TimeUnit.MILLISECONDS);
        return builder.build();
    }
}
