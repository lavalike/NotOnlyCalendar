package com.notonly.calendar.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.notonly.calendar.domain.HistoryResponse
import com.notonly.calendar.ui.holder.HistoryViewHolder
import com.wangzhen.adapter.RecyclerAdapter

/**
 * HistoryAdapter
 * Created by wangzhen on 2020/6/13.
 */
class HistoryAdapter(list: List<HistoryResponse.DataBean>) :
    RecyclerAdapter<HistoryResponse.DataBean>(list) {
    override fun onAbsCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HistoryViewHolder(parent)
    }
}