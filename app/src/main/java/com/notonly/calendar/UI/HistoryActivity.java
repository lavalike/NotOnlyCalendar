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
import com.notonly.calendar.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_history)
public class HistoryActivity extends BaseActivity {
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
        mSwipeRefresh.setRefreshing(true);
        load();
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });
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
        params.setCacheMaxAge(1000 * 60 * 5);//缓存时间5分钟
        x.http().get(params, new Callback.CacheCallback<String>() {
            String result = "";
            boolean hasErr = false;

            @Override
            public boolean onCache(String result) {
                this.result = result;
                return true;
            }

            @Override
            public void onSuccess(String result) {
                this.result = result;
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                this.hasErr = true;
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
                    String errCode = object.getString("error_code");
                    if (!errCode.equals("0")) {
                        ToastUtil.getInstance(mContext).toast("出错啦(ErrCode:" + errCode + ")");
                        return;
                    }
                    List<HistoryBean> list = new Gson().fromJson(object.getString("result"), new TypeToken<ArrayList<HistoryBean>>() {
                    }.getType());
                    mAdapter = new HistoryAdapter(mContext, list);
                    mListView.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
