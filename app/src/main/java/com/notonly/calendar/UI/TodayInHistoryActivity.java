package com.notonly.calendar.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notonly.calendar.R;
import com.notonly.calendar.adapter.HistoryAdapter;
import com.notonly.calendar.bean.HistoryBean;
import com.notonly.calendar.util.Constants;
import com.notonly.calendar.util.DateUtil;
import com.notonly.calendar.util.NetworkUtil;
import com.notonly.calendar.util.ToastUtil;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_today_in_history)
public class TodayInHistoryActivity extends BaseActivity {
    private Context mContext;

    @ViewInject(R.id.SwipeRefresh_History)
    private SwipeRefreshLayout mSwipeRefresh;
    @ViewInject(R.id.ListView_History)
    private ListView mListView;

    private HistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setTitle(getTitle() + "(" + DateUtil.getMonth() + "月" + DateUtil.getDay() + "日)");
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(true);
            }
        });

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });
        load();
    }

    /**
     * 加载数据
     */
    private void load() {
        RequestParams params = new RequestParams(Constants.url_todayinhistory);
        params.addQueryStringParameter("key", Constants.AppKey_todayinhistory);
        params.addQueryStringParameter("v", "1.0");
        params.addQueryStringParameter("month", DateUtil.getMonth());
        params.addQueryStringParameter("day", DateUtil.getDay());
        Callback.Cancelable task = x.http().get(params, new Callback.CommonCallback<String>() {
            String result = "";
            boolean hasErr = false;

            @Override
            public void onSuccess(String result) {
                this.result = result;
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                this.hasErr = true;
                if (!NetworkUtil.isNetworkAvailable(mContext)) {
                    ToastUtil.getInstance(mContext).toast(getString(R.string.error_network));
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                mSwipeRefresh.setRefreshing(false);
                if (this.result.equals("") || this.hasErr)
                    return;
                try {
                    JSONObject object = new JSONObject(this.result);
                    Gson gson = new Gson();
                    List<HistoryBean> list = gson.fromJson(object.getString("result"), new TypeToken<ArrayList<HistoryBean>>() {
                    }.getType());
                    mAdapter = new HistoryAdapter(mContext, list);
                    mListView.setAdapter(mAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        addTaskToList(task);
    }
}
