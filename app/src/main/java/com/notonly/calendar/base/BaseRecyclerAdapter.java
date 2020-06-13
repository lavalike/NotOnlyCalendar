package com.notonly.calendar.base;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Recycler.Adapter基类
 * Created by wangzhen on 2016/11/19.
 */

public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    public List<T> mDatas;
    private OnItemClickListener<T> mItemClickListener;
    private OnItemLongClickListener<T> mItemLongClickListener;

    public BaseRecyclerAdapter(List<T> data) {
        this.mDatas = data;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onMyCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        setItemEvent(holder);
        onMyBindViewHolder(holder, position);
    }

    /**
     * 设置Item的事件
     *
     * @param holder
     */
    private void setItemEvent(VH holder) {
        holder.itemView.setTag(holder);
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(innerClickListener);
        }
        if (mItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(innerLongClickListener);
        }
    }

    public void setData(List<T> data) {
        mDatas = data;
        notifyDataSetChanged();
    }

    public void addData(T data) {
        mDatas.add(data);
        notifyItemInserted(mDatas.size());
    }

    public void addData(List<T> data) {
        int size = mDatas.size();
        mDatas.addAll(data);
        notifyItemRangeChanged(size, data.size());
    }

    private View.OnClickListener innerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            VH holder = (VH) v.getTag();
            int position = holder.getLayoutPosition();
            mItemClickListener.onClick(v, position, mDatas.get(position));
        }
    };

    private View.OnLongClickListener innerLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            VH holder = (VH) v.getTag();
            int position = holder.getLayoutPosition();
            mItemLongClickListener.onLongClick(v, position, mDatas.get(position));
            return true;
        }
    };

    public abstract VH onMyCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onMyBindViewHolder(VH holder, int position);

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> listener) {
        mItemLongClickListener = listener;
    }

    public interface OnItemClickListener<T> {
        void onClick(View view, int position, T data);
    }

    public interface OnItemLongClickListener<T> {
        void onLongClick(View view, int position, T data);
    }
}
