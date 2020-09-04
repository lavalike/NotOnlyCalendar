package com.notonly.calendar.ui.holder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dimeno.adapter.base.RecyclerViewHolder;
import com.notonly.calendar.R;
import com.notonly.calendar.domain.HistoryResponse;

import java.util.Locale;

/**
 * HistoryViewHolder
 * Created by wangzhen on 2020/6/13.
 */
public class HistoryViewHolder extends RecyclerViewHolder<HistoryResponse.DataBean> {

    private TextView mTvDate, mTvTitle;
    private ImageView imageView;

    public HistoryViewHolder(@NonNull ViewGroup parent) {
        super(parent, R.layout.item_history_holder_layout);
        mTvDate = findViewById(R.id.tv_date);
        mTvTitle = findViewById(R.id.tv_title);
        imageView = findViewById(R.id.image);
    }

    @Override
    public void bind() {
        mTvDate.setText(String.format(Locale.CHINA, "%s年%s月%s日", mData.year, mData.month, mData.day));
        mTvTitle.setText(mData.title);
        Glide.with(itemView.getContext()).load(mData.picUrl)
                .apply(new RequestOptions().placeholder(R.mipmap.ic_header).error(R.mipmap.ic_header))
                .into(imageView);
    }
}
