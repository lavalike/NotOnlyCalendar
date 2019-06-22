package com.notonly.calendar.user_interface.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.notonly.calendar.R;
import com.notonly.calendar.base.BaseRecyclerAdapter;
import com.notonly.calendar.domain.HistoryBean;

import java.util.List;

/**
 * 历史上的今天列表
 * Created by wangzhen on 16/1/13.
 */
public class HistoryAdapter extends BaseRecyclerAdapter<HistoryBean.ResultBean, HistoryAdapter.HistoryViewHolder> {

    public HistoryAdapter(List<HistoryBean.ResultBean> data) {
        super(data);
    }

    @Override
    public HistoryViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_layout, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onMyBindViewHolder(HistoryViewHolder holder, int position) {
        HistoryBean.ResultBean bean = mDatas.get(position);
        holder.tv_date.setText(bean.getDate());
        holder.tv_title.setText(bean.getTitle());
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date;
        public TextView tv_title;


        public HistoryViewHolder(View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
