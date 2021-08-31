package com.notonly.calendar.ui.widget.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.notonly.calendar.R;
import com.notonly.calendar.domain.SloganBean;
import com.notonly.calendar.network.task.SloganTask;
import com.notonly.calendar.ui.view.MainActivity;
import com.wangzhen.network.callback.LoadingCallback;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;

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

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_UPDATE) {
                if (mContext != null) {
                    RemoteViews remoteViews = getRemoteViews(mContext);
                    AppWidgetManager manager = AppWidgetManager.getInstance(mContext);
                    ComponentName componentName = new ComponentName(mContext, EnglishEverydayWidget.class);
                    manager.updateAppWidget(componentName, remoteViews);
                }
            }
        }
    };

    @NonNull
    private static RemoteViews getRemoteViews(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.english_widget);
        english += english;
        english += english;
        english += english;

        chinese += chinese;
        chinese += chinese;
        chinese += chinese;

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
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        this.mContext = context;
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        request();
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        request();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        appWidgetManager.updateAppWidget(appWidgetId, getRemoteViews(context));
    }

    private void request() {
        new SloganTask(new LoadingCallback<SloganBean>() {
            @Override
            public void onSuccess(SloganBean data) {
                english = data.getContent();
                chinese = data.getNote();
                picture = data.getPicture();
                mHandler.sendEmptyMessage(MSG_UPDATE);

                createBitmap(picture, new BitmapCallback() {
                    @Override
                    public void onFinish(Bitmap data) {
                        bitmap = data;
                        mHandler.sendEmptyMessage(MSG_UPDATE);
                    }
                });
            }
        }).setTag(mContext).exe();
    }

    /**
     * 根据url请求bitmap
     *
     * @param picture url
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
}

