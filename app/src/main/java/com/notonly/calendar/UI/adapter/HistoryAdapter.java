package com.notonly.calendar.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.notonly.calendar.R;
import com.notonly.calendar.domain.HistoryBean;

import java.util.List;

/**
 * 历史上的今天列表
 * Created by wangzhen on 16/1/13.
 */
public class HistoryAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<HistoryBean.ResultBean> mDatas;

    public HistoryAdapter(Context context, List<HistoryBean.ResultBean> data) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.mDatas = data;
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        HistoryViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_history_layout, viewGroup, false);
            holder = new HistoryViewHolder(view);
            view.setTag(holder);
        } else
            holder = (HistoryViewHolder) view.getTag();
        HistoryBean.ResultBean bean = mDatas.get(i);
        holder.tv_date.setText(bean.getDate());
        holder.tv_title.setText(bean.getTitle());
        holder.data = bean;
        return view;
    }

    public class HistoryViewHolder {
        public TextView tv_date;
        public TextView tv_title;
        public HistoryBean.ResultBean data;

        public HistoryViewHolder(View view) {
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
        }
    }
}
