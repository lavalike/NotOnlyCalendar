package com.notonly.calendar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.notonly.calendar.R;
import com.notonly.calendar.bean.HistoryBean;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by wangzhen on 16/1/13.
 */
public class HistoryAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<HistoryBean> mDatas;
    private ImageOptions mOptions;

    public HistoryAdapter(Context context, List<HistoryBean> data) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.mDatas = data;
        mOptions = new ImageOptions.Builder().setLoadingDrawableId(R.mipmap.ic_launcher).build();
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
        //复位
        holder.iv_pic.setVisibility(View.VISIBLE);
        HistoryBean bean = mDatas.get(i);
        if (bean.getPic().equals(""))
            holder.iv_pic.setVisibility(View.GONE);
        else
            x.image().bind(holder.iv_pic, bean.getPic(), mOptions);
        holder.tv_date.setText(bean.getYear() + "年" + bean.getMonth() + "月" + bean.getDay() + "日");
        holder.tv_title.setText(bean.getTitle());
        holder.tv_des.setText(bean.getDes());
        holder.data = bean;
        return view;
    }

    public class HistoryViewHolder {
        public ImageView iv_pic;
        public TextView tv_date;
        public TextView tv_title;
        public TextView tv_des;
        public HistoryBean data;

        public HistoryViewHolder(View view) {
            iv_pic = (ImageView) view.findViewById(R.id.iv_item_pic);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_des = (TextView) view.findViewById(R.id.tv_des);
        }
    }
}
