package com.notonly.calendar.user_interface.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dimeno.adapter.base.RecyclerItem;
import com.dimeno.network.callback.LoadingCallback;
import com.notonly.calendar.R;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.base.toolbar.ToolBarCommonHolder;
import com.notonly.calendar.domain.HistoryResponse;
import com.notonly.calendar.network.task.HistoryTask;
import com.notonly.calendar.user_interface.adapter.HistoryAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 历史上的今天
 * created by wangzhen on 2016/11/18
 */
public class HistoryActivity extends BaseActivity {
    @BindView(R.id.SwipeRefresh_History)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        initRecycler();
        initSwipeRefresh();
        request();
    }

    private void request() {
        startLoading();
        new HistoryTask(new LoadingCallback<HistoryResponse>() {
            @Override
            public void onSuccess(HistoryResponse response) {
                bind(response.data);
            }

            @Override
            public void onComplete() {
                stopLoading();
            }
        }).setTag(this).exe(HistoryTask.TYPE_NO_DETAILS);
    }

    private void bind(List<HistoryResponse.DataBean> data) {
        HistoryAdapter adapter = new HistoryAdapter(data);
        adapter.addFooter(new RecyclerItem() {
            @Override
            public int layout() {
                return R.layout.layout_history_footer_layout;
            }

            @Override
            public void onViewCreated(View itemView) {

            }
        }.onCreateView(mRecycler));
        adapter.setEmpty(new RecyclerItem() {
            @Override
            public int layout() {
                return R.layout.emtpy_layout;
            }

            @Override
            public void onViewCreated(View itemView) {

            }
        }.onCreateView(mRecycler));
        mRecycler.setAdapter(adapter);
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.title_todayinhistory));
    }

    private void initRecycler() {
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private void initSwipeRefresh() {
        //设置加载图标颜色
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_purple, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefresh.setOnRefreshListener(this::request);
    }

    public void startLoading() {
        mSwipeRefresh.post(() -> mSwipeRefresh.setRefreshing(true));
    }

    public void stopLoading() {
        mSwipeRefresh.post(() -> mSwipeRefresh.setRefreshing(false));
    }
}
