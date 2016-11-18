package com.notonly.calendar.UI.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.notonly.calendar.R;
import com.notonly.calendar.UI.adapter.HistoryAdapter;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.base.helper.APIKey;
import com.notonly.calendar.base.helper.ErrHelper;
import com.notonly.calendar.base.manager.APIManager;
import com.notonly.calendar.domain.HistoryBean;
import com.notonly.calendar.util.DateUtil;
import com.notonly.calendar.util.T;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * 历史上的今天列表
 * created by wangzhen on 2016/11/18
 */
public class HistoryListActivity extends BaseActivity {
    @BindView(R.id.SwipeRefresh_History)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.ListView_History)
    ListView mListView;

    private HistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_in_history);
        ButterKnife.bind(this);
        initSwipeRefresh();
        startLoading();
        load();
    }

    private void initSwipeRefresh() {
        //设置加载图标颜色
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_purple, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
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
        RequestParams params = new RequestParams(APIManager.URL_TODAY_ON_HISTORY_V2);
        params.addQueryStringParameter("key", APIKey.AppKey_todayinhistory);
        params.addQueryStringParameter("date", DateUtil.getMonth() + "/" + DateUtil.getDay());
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
                ErrHelper.check(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                stopLoading();
                if (this.result.equals("") || this.hasErr)
                    return;
                try {
                    Gson gson = new Gson();
                    HistoryBean list = gson.fromJson(this.result, HistoryBean.class);
                    if (list != null && list.getResult() != null) {
                        mAdapter = new HistoryAdapter(mContext, list.getResult());
                        mListView.setAdapter(mAdapter);
                    } else {
                        if (list.getReason() != null)
                            T.get(mContext).toast(list.getReason());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        addTaskToList(task);
    }

    @OnItemClick(value = {R.id.ListView_History})
    public void onListViewHistoryItemClick(AdapterView<?> parent, View view, int position, long id) {
        final HistoryBean.ResultBean data = ((HistoryAdapter.HistoryViewHolder) view.getTag()).data;
        Intent intent = new Intent(mContext, HistoryDetailActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }

    public void startLoading() {
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(true);
            }
        });
    }

    public void stopLoading() {
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }
}
