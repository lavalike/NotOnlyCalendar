package com.notonly.calendar.ui.holder

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.notonly.calendar.R
import com.notonly.calendar.databinding.ItemHistoryHolderLayoutBinding
import com.notonly.calendar.domain.HistoryResponse
import com.wangzhen.adapter.base.RecyclerViewHolder
import java.util.*

/**
 * HistoryViewHolder
 * Created by wangzhen on 2020/6/13.
 */
class HistoryViewHolder(parent: ViewGroup) :
    RecyclerViewHolder<HistoryResponse.DataBean>(parent, R.layout.item_history_holder_layout) {

    private var binding: ItemHistoryHolderLayoutBinding =
        ItemHistoryHolderLayoutBinding.bind(itemView)

    override fun bind() {
        mData?.let { data ->
            binding.tvDate.text = String.format(
                Locale.CHINA,
                "%s年%s月%s日",
                data.year,
                data.month,
                data.day
            )
            binding.tvTitle.text = data.title
            Glide.with(itemView.context).load(data.picUrl)
                .apply(RequestOptions().placeholder(R.mipmap.ic_header).error(R.mipmap.ic_header))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.image)
        }
    }
}