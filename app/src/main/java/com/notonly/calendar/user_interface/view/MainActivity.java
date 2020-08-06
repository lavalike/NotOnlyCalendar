package com.notonly.calendar.user_interface.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dimeno.network.callback.LoadingCallback;
import com.notonly.calendar.R;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.base.Constants;
import com.notonly.calendar.base.helper.SPHelper;
import com.notonly.calendar.base.helper.SPKey;
import com.notonly.calendar.base.manager.UpdateManager;
import com.notonly.calendar.domain.CalendarBean;
import com.notonly.calendar.domain.SloganBean;
import com.notonly.calendar.network.task.CalendarTask;
import com.notonly.calendar.network.task.SloganTask;
import com.notonly.calendar.util.DateUtil;
import com.notonly.calendar.util.T;
import com.notonly.calendar.util.WxUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * 主页面
 * created by wangzhen on 2016/11/18
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_slogan_cn)
    TextView tvSloganCN;
    @BindView(R.id.tv_slogan_en)
    TextView tvSloganEN;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_anim_day)
    TextView tvAnimDay;
    @BindView(R.id.tv_avoid)
    TextView tvAvoid;
    @BindView(R.id.tv_suit)
    TextView tvSuit;
    @BindView(R.id.view_anim_breathe)
    View viewAnimBreathe;
    @BindView(R.id.tv_lunar)
    TextView tvLunar;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.tv_type_des)
    TextView tvTypeDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        findSlogan();
        findCalendar();
        UpdateManager.get(this).check();
    }

    private void init() {
        String sloganCN = SPHelper.getInstance().get(SPKey.KEY_SLOGAN_CN, "");
        String sloganEN = SPHelper.getInstance().get(SPKey.KEY_SLOGAN_EN, "");
        String picture = SPHelper.getInstance().get(SPKey.KEY_SLOGAN_PICTURE, "");

        String avoid = SPHelper.getInstance().get(SPKey.KEY_AVOID, "");
        String suit = SPHelper.getInstance().get(SPKey.KEY_SUIT, "");
        String lunar = SPHelper.getInstance().get(SPKey.KEY_LUNAR, "");
        String typeDes = SPHelper.getInstance().get(SPKey.KEY_TYPE_DES, "");
        String calendarDetail = SPHelper.getInstance().get(SPKey.KEY_CALENDAR_DETAIL, "");

        tvSloganCN.setText(sloganCN);
        tvSloganEN.setText(sloganEN);
        Glide.with(this).load(picture).apply(
                RequestOptions.bitmapTransform(new BlurTransformation(Constants.BLUR_RADIUS)).placeholder(R.mipmap.ic_header).error(R.mipmap.ic_header)
        ).into(ivCover);
        tvAnimDay.setText(DateUtil.getDay());
        tvLunar.setText(lunar);
        tvTypeDes.setText(typeDes);
        tvDetail.setText(calendarDetail);
        tvAvoid.setText(avoid);
        tvSuit.setText(suit);
    }

    /**
     * 加载标语
     */
    private void findSlogan() {
        new SloganTask(new LoadingCallback<SloganBean>() {
            @Override
            public void onSuccess(SloganBean data) {
                final String english = data.getContent();
                final String chinese = data.getNote();
                String picture = data.getPicture2();

                tvSloganEN.setText(english);
                tvSloganCN.setText(chinese);
                if (!isDestroyed()) {
                    Glide.with(mContext).load(picture).apply(
                            RequestOptions.bitmapTransform(new BlurTransformation(Constants.BLUR_RADIUS)).placeholder(R.mipmap.ic_header).error(R.mipmap.ic_header)
                    ).into(ivCover);
                }
                SPHelper.getInstance()
                        .put(SPKey.KEY_SLOGAN_EN, english)
                        .put(SPKey.KEY_SLOGAN_CN, chinese)
                        .put(SPKey.KEY_SLOGAN_PICTURE, picture)
                        .commit();
            }

            @Override
            public void onError(int code, String message) {
                super.onError(code, message);
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        }).setTag(this).exe();
    }

    /**
     * 获取指定日期的节假日及万年历信息
     */
    private void findCalendar() {
        new CalendarTask(new LoadingCallback<CalendarBean>() {
            @Override
            public void onSuccess(CalendarBean bean) {
                CalendarBean.DataBean data = bean.getData();
                if (data == null) {
                    T.get(mContext).toast(getString(R.string.error_connect_timeout_lovely));
                    return;
                }
                String weekDayCN = data.getWeekDayCN();
                String chineseZodiac = data.getChineseZodiac();
                String yearTips = data.getYearTips();
                String lunarCalendar = data.getLunarCalendar();
                String typeDes = DateUtil.formatDateTime("yyyy年MM月dd日") + " " + data.getTypeDes();
                String solarTerms = data.getSolarTerms();
                String avoid = data.getAvoid();
                String suit = data.getSuit();
                int dayOfYear = data.getDayOfYear();
                int weekOfYear = data.getWeekOfYear();

                StringBuilder builder = new StringBuilder();
                builder.append(weekDayCN);
                builder.append(" ");
                builder.append(yearTips);
                builder.append("[");
                builder.append(chineseZodiac);
                builder.append("]");
                builder.append("年");
                builder.append(" ");
                builder.append(solarTerms);
                builder.append("\n");
                builder.append(DateUtil.getYear());
                builder.append("年第");
                builder.append(dayOfYear);
                builder.append("天、第");
                builder.append(weekOfYear);
                builder.append("周");
                String detail = builder.toString();

                SPHelper.getInstance()
                        .put(SPKey.KEY_SUIT, suit)
                        .put(SPKey.KEY_AVOID, avoid)
                        .put(SPKey.KEY_LUNAR, lunarCalendar)
                        .put(SPKey.KEY_TYPE_DES, typeDes)
                        .put(SPKey.KEY_CALENDAR_DETAIL, detail)
                        .commit();

                init();
            }

            @Override
            public void onError(int code, String message) {
                T.get(mContext).toast(message);
            }
        }).setDate(DateUtil.formatDateTime("yyyyMMdd")).setTag(this).exe();
    }

    @Override
    public boolean showToolbar() {
        return false;
    }

    @OnClick({R.id.menu_calendar, R.id.menu_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_calendar:
                startActivity(new Intent(mContext, HistoryActivity.class));
                break;
            case R.id.menu_setting:
                startActivity(new Intent(mContext, AboutActivity.class));
                break;
        }
    }

    public void openMiniProgram(View view) {
        WxUtils.openMiniProgram(this);
    }
}
