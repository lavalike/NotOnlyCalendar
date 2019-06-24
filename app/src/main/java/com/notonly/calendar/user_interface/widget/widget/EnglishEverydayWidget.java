package com.notonly.calendar.user_interface.widget.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.notonly.calendar.R;
import com.notonly.calendar.api.APIService;
import com.notonly.calendar.base.manager.APIManager;
import com.notonly.calendar.base.retrofit.RetrofitManager;
import com.notonly.calendar.domain.SloganBean;
import com.notonly.calendar.user_interface.view.MainActivity;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * EnglishEverydayWidget 每日英语插件
 * Created by wangzhen on 2019-06-22.
 */
public class EnglishEverydayWidget extends AppWidgetProvider {

    private Context mContext;
    private static final int MSG_UPDATE = 0;
    private static String english;
    private static String chinese;
    private static String picture;
    private static Bitmap bitmap;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE:
                    if (mContext != null) {
                        RemoteViews remoteViews = getRemoteViews(mContext);
                        AppWidgetManager manager = AppWidgetManager.getInstance(mContext);
                        ComponentName componentName = new ComponentName(mContext, EnglishEverydayWidget.class);
                        manager.updateAppWidget(componentName, remoteViews);
                    }
                    break;
            }
        }
    };

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        appWidgetManager.updateAppWidget(appWidgetId, getRemoteViews(context));
    }

    @NonNull
    private static RemoteViews getRemoteViews(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.english_widget);
        views.setTextViewText(R.id.tv_english, english);
        views.setTextViewText(R.id.tv_chinese, chinese);
        if (bitmap == null)
            views.setImageViewResource(R.id.iv, R.mipmap.ic_header);
        else
            views.setImageViewBitmap(R.id.iv, bitmap);
        views.setOnClickPendingIntent(R.id.root, PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT));
        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        this.mContext = context;
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        request();
    }

    /**
     * 请求每日一句
     */
    private void request() {
        APIService apiService = RetrofitManager.getClient().create(APIService.class);
        Call<SloganBean> call = apiService.findSlogan(APIManager.URL_SLOGAN);
        call.enqueue(new Callback<SloganBean>() {
            @Override
            public void onResponse(Call<SloganBean> call, Response<SloganBean> response) {
                if (response.isSuccessful()) {
                    SloganBean data = response.body();
                    english = data.getContent();
                    chinese = data.getNote();
                    picture = data.getPicture();
                    createBitmap(picture, new BitmapCallback() {
                        @Override
                        public void onFinish(Bitmap data) {
                            bitmap = data;
                            mHandler.sendEmptyMessage(MSG_UPDATE);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<SloganBean> call, Throwable t) {

            }
        });
    }

    /**
     * 根据url请求bitmap
     *
     * @param picture url
     * @return bitmap
     */
    private void createBitmap(final String picture, final BitmapCallback callback) {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(picture).openConnection();
                    if (conn.getResponseCode() == 200) {
                        InputStream is = conn.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);
                        bitmap = BitmapFactory.decodeStream(bis);
                        is.close();
                        bis.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (callback != null)
                        callback.onFinish(bitmap);
                }
            }
        });
    }

    public interface BitmapCallback {
        void onFinish(Bitmap data);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        mHandler.sendEmptyMessage(MSG_UPDATE);
    }
}

