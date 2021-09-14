package com.notonly.calendar.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.notonly.calendar.R
import com.notonly.calendar.base.BaseActivity
import com.notonly.calendar.base.Constants
import com.notonly.calendar.base.helper.SPKey
import com.notonly.calendar.base.manager.UpdateManager
import com.notonly.calendar.databinding.ActivityMainBinding
import com.notonly.calendar.domain.CalendarBean
import com.notonly.calendar.domain.SloganBean
import com.notonly.calendar.network.task.CalendarTask
import com.notonly.calendar.network.task.SloganTask
import com.notonly.calendar.util.DateUtil
import com.notonly.calendar.util.T
import com.notonly.calendar.util.WxUtils
import com.wangzhen.commons.storage.SPHelper
import com.wangzhen.network.callback.LoadingCallback
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * 主页面
 * created by wangzhen on 2016/11/18
 */
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).apply {
            binding = this
        }.root)
        initViews()
        bindData()
        findSlogan()
        findCalendar()
        UpdateManager.get(this).check()
    }

    private fun initViews() {
        with(binding) {
            menuCalendar.setOnClickListener {
                startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
            }
            menuSetting.setOnClickListener {
                startActivity(Intent(this@MainActivity, SettingActivity::class.java))
            }
            btnOpenMiniProgram.setOnClickListener { WxUtils.openMiniProgram(this@MainActivity) }
        }
    }

    private fun bindData() {
        val sloganCN: String = SPHelper.get().get(SPKey.KEY_SLOGAN_CN, "")
        val sloganEN: String = SPHelper.get().get(SPKey.KEY_SLOGAN_EN, "")
        val picture: String = SPHelper.get().get(SPKey.KEY_SLOGAN_PICTURE, "")
        val avoid: String = SPHelper.get().get(SPKey.KEY_AVOID, "")
        val suit: String = SPHelper.get().get(SPKey.KEY_SUIT, "")
        val lunar: String = SPHelper.get().get(SPKey.KEY_LUNAR, "")
        val typeDes: String = SPHelper.get().get(SPKey.KEY_TYPE_DES, "")
        val calendarDetail: String = SPHelper.get().get(SPKey.KEY_CALENDAR_DETAIL, "")
        binding.tvSloganCn.text = sloganCN
        binding.tvSloganEn.text = sloganEN
        Glide.with(this).load(picture).apply(
            RequestOptions.bitmapTransform(BlurTransformation(Constants.BLUR_RADIUS))
                .placeholder(R.mipmap.ic_header).error(R.mipmap.ic_header)
        ).transition(DrawableTransitionOptions.withCrossFade()).into(binding.ivCover)
        binding.tvLunar.text = lunar
        binding.tvTypeDes.text = typeDes
        binding.tvDetail.text = calendarDetail
        binding.tvAvoid.text = avoid
        binding.tvSuit.text = suit
    }

    private fun findSlogan() {
        SloganTask(object : LoadingCallback<SloganBean>() {
            override fun onSuccess(data: SloganBean) {
                val english: String = data.content
                val chinese: String = data.note
                val picture: String = data.picture2
                binding.tvSloganEn.text = english
                binding.tvSloganCn.text = chinese
                if (!isDestroyed) {
                    Glide.with(this@MainActivity).load(picture).apply(
                        RequestOptions.bitmapTransform(BlurTransformation(Constants.BLUR_RADIUS))
                            .placeholder(R.mipmap.ic_header).error(R.mipmap.ic_header)
                    ).transition(DrawableTransitionOptions.withCrossFade()).into(binding.ivCover)
                }
                SPHelper.get()
                    .put(SPKey.KEY_SLOGAN_EN, english)
                    .put(SPKey.KEY_SLOGAN_CN, chinese)
                    .put(SPKey.KEY_SLOGAN_PICTURE, picture)
                    .commit()
            }

            override fun onError(code: Int, message: String) {
                super.onError(code, message)
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
            }
        }).setTag(this).exe()
    }

    private fun findCalendar() {
        CalendarTask(object : LoadingCallback<CalendarBean>() {
            override fun onSuccess(bean: CalendarBean) {
                val data = bean.data
                if (data == null) {
                    T.get(this@MainActivity).toast(getString(R.string.error_connect_timeout_lovely))
                    return
                }
                val weekDayCN: String = data.weekDayCN
                val chineseZodiac: String = data.chineseZodiac
                val yearTips: String = data.yearTips
                val lunarCalendar: String = data.lunarCalendar
                val typeDes: String =
                    DateUtil.formatDateTime("yyyy年MM月dd日") + " " + data.typeDes
                val solarTerms: String = data.solarTerms
                val avoid: String = data.avoid
                val suit: String = data.suit
                val dayOfYear: Int = data.dayOfYear
                val weekOfYear: Int = data.weekOfYear
                val builder = StringBuilder()
                builder.append(weekDayCN)
                builder.append(" ")
                builder.append(yearTips)
                builder.append("[")
                builder.append(chineseZodiac)
                builder.append("]")
                builder.append("年")
                builder.append(" ")
                builder.append(solarTerms)
                builder.append("\n")
                builder.append(DateUtil.getYear())
                builder.append("年第")
                builder.append(dayOfYear)
                builder.append("天、第")
                builder.append(weekOfYear)
                builder.append("周")
                val detail = builder.toString()
                SPHelper.get()
                    .put(SPKey.KEY_SUIT, suit)
                    .put(SPKey.KEY_AVOID, avoid)
                    .put(SPKey.KEY_LUNAR, lunarCalendar)
                    .put(SPKey.KEY_TYPE_DES, typeDes)
                    .put(SPKey.KEY_CALENDAR_DETAIL, detail)
                    .commit()
                bindData()
            }

            override fun onError(code: Int, message: String) {
                T.get(this@MainActivity).toast(message)
            }
        }).setDate(DateUtil.formatDateTime("yyyyMMdd")).setTag(this).exe()
    }
}