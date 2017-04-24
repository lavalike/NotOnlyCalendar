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

import com.notonly.calendar.R;
import com.notonly.calendar.UI.adapter.HistoryAdapter;
import com.notonly.calendar.api.APIService;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.base.BaseRecyclerAdapter;
import com.notonly.calendar.base.helper.APIKey;
import com.notonly.calendar.base.helper.ErrHelper;
import com.notonly.calendar.base.retrofit.RetrofitManager;
import com.notonly.calendar.base.toolbar.ToolBarRightIconHolder;
import com.notonly.calendar.domain.HistoryBean;
import com.notonly.calendar.util.DateUtil;
import com.notonly.calendar.util.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

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
        findHistoryList();
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
                findHistoryList();
            }
        });
    }

    /**
     * 加载数据
     */
    private void findHistoryList() {
        APIService apiService = RetrofitManager.getClient().create(APIService.class);
        String date = DateUtil.getMonth() + "/" + DateUtil.getDay();
        String key = APIKey.AppKey_todayinhistory;
        Call<HistoryBean> call = apiService.findHistoryList(date, key);
        call.enqueue(new retrofit2.Callback<HistoryBean>() {
            @Override
            public void onResponse(Call<HistoryBean> call, Response<HistoryBean> response) {
                stopLoading();
                if (!response.isSuccessful()) return;
                HistoryBean result = response.body();
                if (result != null && result.getResult() != null) {
                    if (mAdapter == null) {
                        mAdapter = new HistoryAdapter(result.getResult());
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
                        mAdapter.setData(result.getResult());
                    }
                } else {
                    if (result.getReason() != null)
                        T.get(mContext).toast(result.getReason());
                }
            }

            @Override
            public void onFailure(Call<HistoryBean> call, Throwable t) {
                ErrHelper.check(t);
            }
        });
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
