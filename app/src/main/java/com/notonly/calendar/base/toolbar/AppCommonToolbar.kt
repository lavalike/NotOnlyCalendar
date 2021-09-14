package com.notonly.calendar.base.toolbar

import android.app.Activity
import android.view.View
import com.notonly.calendar.R
import com.notonly.calendar.databinding.ToolbarAppCommonLayoutBinding
import com.wangzhen.commons.toolbar.impl.Toolbar

/**
 * app common toolbar
 * Created by wangzhen on 2020/10/30.
 */
class AppCommonToolbar(activity: Activity, private val title: String?) : Toolbar(activity) {
    private lateinit var binding: ToolbarAppCommonLayoutBinding

    override fun layoutRes(): Int {
        return R.layout.toolbar_app_common_layout
    }

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
        binding = ToolbarAppCommonLayoutBinding.bind(view)
        binding.back.setOnClickListener {
            activity.finish()
        }
        binding.title.text = title
    }

    fun setTitle(title: String) {
        binding.title.text = title
    }
}