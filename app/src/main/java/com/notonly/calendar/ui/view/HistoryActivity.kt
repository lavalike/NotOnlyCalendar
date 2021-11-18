package com.notonly.calendar.ui.view

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.notonly.calendar.R
import com.notonly.calendar.base.BaseActivity
import com.notonly.calendar.base.toolbar.AppCommonToolbar
import com.notonly.calendar.databinding.ActivityHistoryBinding
import com.notonly.calendar.domain.HistoryResponse
import com.notonly.calendar.network.task.HistoryTask
import com.notonly.calendar.ui.adapter.HistoryAdapter
import com.wangzhen.adapter.base.RecyclerItem
import com.wangzhen.commons.toolbar.impl.Toolbar
import com.wangzhen.network.callback.LoadingCallback
import com.wangzhen.network.loading.DefaultLoadingPage

/**
 * 历史上的今天
 * created by wangzhen on 2016/11/18
 */
class HistoryActivity : BaseActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityHistoryBinding.inflate(layoutInflater).apply {
            binding = this
        }.root)
        initRecycler()
        initSwipeRefresh()
        request(true)
    }

    private fun request(loading: Boolean = false) {
        startLoading()
        HistoryTask(object : LoadingCallback<HistoryResponse>() {
            override fun onSuccess(response: HistoryResponse) {
                response.data?.let { data ->
                    bind(data)
                }
            }

            override fun onComplete() {
                stopLoading()
            }
        }).setTag(this)
            .setLoadingPage(if (loading) DefaultLoadingPage(binding.refresh) else null)
            .exe(HistoryTask.TYPE_NO_DETAILS)
    }

    private fun bind(data: List<HistoryResponse.DataBean>) {
        val adapter = HistoryAdapter(data).apply {
            addFooter(object : RecyclerItem() {
                override fun layout(): Int {
                    return R.layout.layout_history_footer_layout
                }
            }.onCreateView(binding.recycler))
            setEmpty(object : RecyclerItem() {
                override fun layout(): Int {
                    return R.layout.emtpy_layout
                }
            }.onCreateView(binding.recycler))
        }
        binding.recycler.adapter = adapter
    }

    override fun createToolbar(): Toolbar {
        return AppCommonToolbar(this, getString(R.string.title_todayinhistory))
    }

    private fun initRecycler() {
        binding.recycler.layoutManager = LinearLayoutManager(this)
    }

    private fun initSwipeRefresh() {
        binding.refresh.setColorSchemeResources(
            R.color.colorPrimary, android.R.color.holo_purple, android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
        binding.refresh.setOnRefreshListener(::request)
    }

    private fun startLoading() {
        binding.refresh.post { binding.refresh.isRefreshing = true }
    }

    private fun stopLoading() {
        binding.refresh.post { binding.refresh.isRefreshing = false }
    }
}