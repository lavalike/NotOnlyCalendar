package com.notonly.calendar.UI.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.notonly.calendar.R;
import com.notonly.calendar.UI.adapter.HistoryAdapter;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.base.BaseRecyclerAdapter;
import com.notonly.calendar.base.helper.APIKey;
import com.notonly.calendar.base.helper.ErrHelper;
import com.notonly.calendar.base.manager.APIManager;
import com.notonly.calendar.base.toolbar.ToolBarRightIconHolder;
import com.notonly.calendar.domain.HistoryBean;
import com.notonly.calendar.util.DateUtil;
import com.notonly.calendar.util.T;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 历史上的今天列表
 * created by wangzhen on 2016/11/18
 */
public class HistoryListActivity extends BaseActivity {
    @BindView(R.id.SwipeRefresh_History)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.recycler_hitorylist)
    RecyclerView mRecycler;

    private HistoryAdapter mAdapter;
    private LinearLayoutManager managerLinear;
    private StaggeredGridLayoutManager managerStagger;
    private static final int TYPE_LINER = 0x01;
    private static final int TYPE_STAGGER = 0x02;
    private int mCurrentType = TYPE_LINER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_in_history);
        ButterKnife.bind(this);
        initRecycler();
        initSwipeRefresh();
        startLoading();
        load();
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        ToolBarRightIconHolder holder = new ToolBarRightIconHolder(this, toolbar, getString(R.string.title_todayinhistory), R.mipmap.ic_grid);
        final ImageView rightMenu = holder.getRightMenu();
        rightMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentType == TYPE_LINER) {
                    mCurrentType = TYPE_STAGGER;
                    rightMenu.setImageResource(R.mipmap.ic_linear);
                    mRecycler.setLayoutManager(managerStagger);
                } else {
                    mCurrentType = TYPE_LINER;
                    rightMenu.setImageResource(R.mipmap.ic_grid);
                    mRecycler.setLayoutManager(managerLinear);
                }
            }
        });
    }

    /**
     * 设置RecyclerView的布局
     */
    private void initRecycler() {
        managerLinear = new LinearLayoutManager(mContext);
        managerLinear.setOrientation(LinearLayoutManager.VERTICAL);
        managerStagger = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(managerLinear);
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
                        if (mAdapter == null) {
                            mAdapter = new HistoryAdapter(list.getResult());
                            mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<HistoryBean.ResultBean>() {
                                @Override
                                public void onClick(View view, int position, HistoryBean.ResultBean data) {
                                    Intent intent = new Intent(mContext, HistoryDetailActivity.class);
                                    intent.putExtra("data", data);
                                    startActivity(intent);
                                }
                            });
                            mRecycler.setAdapter(mAdapter);
                        } else {
                            mAdapter.setData(list.getResult());
                        }
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
