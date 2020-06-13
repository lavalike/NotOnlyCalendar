package com.notonly.calendar.user_interface.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.notonly.calendar.R;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.base.toolbar.ToolBarRightIconHolder;
import com.notonly.calendar.user_interface.adapter.HistoryAdapter;

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
