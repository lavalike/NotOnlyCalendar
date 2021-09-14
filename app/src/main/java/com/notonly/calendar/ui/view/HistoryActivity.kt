package com.notonly.calendar.ui.view;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.wangzhen.adapter.base.RecyclerItem;
import com.wangzhen.commons.toolbar.impl.Toolbar;
import com.wangzhen.network.callback.LoadingCallback;
import com.wangzhen.network.loading.DefaultLoadingPage;
import com.notonly.calendar.R;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.base.toolbar.AppCommonToolbar;
import com.notonly.calendar.domain.HistoryResponse;
import com.notonly.calendar.network.task.HistoryTask;
import com.notonly.calendar.ui.adapter.HistoryAdapter;

import org.jetbrains.annotations.Nullable;

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
        request(true);
    }

    private void request() {
        request(false);
    }

    private void request(boolean loading) {
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
        }).setTag(this).setLoadingPage(loading ? new DefaultLoadingPage(mSwipeRefresh) : null).exe(HistoryTask.TYPE_NO_DETAILS);
    }

    private void bind(List<HistoryResponse.DataBean> data) {
        HistoryAdapter adapter = new HistoryAdapter(data);
        adapter.addFooter(new RecyclerItem() {
            @Override
            public int layout() {
                return R.layout.layout_history_footer_layout;
            }
        }.onCreateView(mRecycler));
        adapter.setEmpty(new RecyclerItem() {
            @Override
            public int layout() {
                return R.layout.emtpy_layout;
            }
        }.onCreateView(mRecycler));
        mRecycler.setAdapter(adapter);
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return new AppCommonToolbar(this, getString(R.string.title_todayinhistory));
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
