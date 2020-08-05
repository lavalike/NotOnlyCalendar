package com.notonly.calendar.user_interface.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
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
import com.notonly.calendar.util.DisplayUtil;

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
    private Paint mPaint;
    private int mSpace;

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
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#EDEDED"));
        mSpace = DisplayUtil.dip2px(this, 0.5f);
        mRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = parent.getChildAt(i);
                    if (childAt != null && i < childCount - 1) {
                        int left = childAt.getLeft();
                        int right = childAt.getRight();
                        int top = childAt.getBottom();
                        int bottom = top + mSpace;
                        c.drawRect(new Rect(left, top, right, bottom), mPaint);
                    }
                }
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if (parent.getAdapter() == null) {
                    return;
                }
                if (position == parent.getAdapter().getItemCount() - 1) {
                    outRect.set(0, 0, 0, 0);
                } else {
                    outRect.set(0, 0, 0, mSpace);
                }
            }
        });
    }

    private void initSwipeRefresh() {
        //设置加载图标颜色
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_purple, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                request();
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
