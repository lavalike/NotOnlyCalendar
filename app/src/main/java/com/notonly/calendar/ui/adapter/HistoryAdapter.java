package com.notonly.calendar.ui.adapter;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.notonly.calendar.domain.HistoryResponse;
import com.notonly.calendar.ui.holder.HistoryViewHolder;
import com.wangzhen.adapter.RecyclerAdapter;

import java.util.List;

/**
 * HistoryAdapter
 * Created by wangzhen on 2020/6/13.
 */
public class HistoryAdapter extends RecyclerAdapter<HistoryResponse.DataBean> {

    public HistoryAdapter(List<HistoryResponse.DataBean> list) {
        super(list);
    }

    @Override
    public RecyclerView.ViewHolder onAbsCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryViewHolder(parent);
    }
}
