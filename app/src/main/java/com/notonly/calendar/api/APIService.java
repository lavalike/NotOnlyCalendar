package com.notonly.calendar.api;

import com.notonly.calendar.domain.CalendarBean;
import com.notonly.calendar.domain.SloganBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * 接口
 * Created by wangzhen on 2017/4/24.
 */
public interface APIService {
    /**
     * 万年历
     *
     * @param date date
     * @return data
     */
    @GET("holiday/single/{date}")
    Call<CalendarBean> findCalendar(@Path("date") String date);

    /**
     * 金山词霸每日一句
     *
     * @param url url
     * @return data
     */
    @GET
    Call<SloganBean> findSlogan(@Url String url);

}
