package com.notonly.calendar.user_interface.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.notonly.calendar.R;
import com.notonly.calendar.api.APIService;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.base.BaseApplication;
import com.notonly.calendar.base.helper.ErrHelper;
import com.notonly.calendar.base.helper.SPHelper;
import com.notonly.calendar.base.helper.SPKey;
import com.notonly.calendar.base.manager.APIManager;
import com.notonly.calendar.base.manager.UpdateManager;
import com.notonly.calendar.base.retrofit.RetrofitManager;
import com.notonly.calendar.domain.CalendarBean;
import com.notonly.calendar.domain.SloganBean;
import com.notonly.calendar.util.DateUtil;
import com.notonly.calendar.util.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 主页面
 * created by wangzhen on 2016/11/18
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_slogan_cn)
    TextView tvSloganCN;
    @BindView(R.id.tv_slogan_en)
    TextView tvSloganEN;
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

        String avoid = SPHelper.getInstance().get(SPKey.KEY_AVOID, "");
        String suit = SPHelper.getInstance().get(SPKey.KEY_SUIT, "");
        String lunar = SPHelper.getInstance().get(SPKey.KEY_LUNAR, "");
        String typeDes = SPHelper.getInstance().get(SPKey.KEY_TYPE_DES, "");
        String calendarDetail = SPHelper.getInstance().get(SPKey.KEY_CALENDAR_DETAIL, "");

        tvSloganCN.setText(sloganCN);
        tvSloganEN.setText(sloganEN);
        tvAnimDay.setText(DateUtil.getDay());
        tvLunar.setText(lunar);
        tvTypeDes.setText(typeDes);
        tvDetail.setText(calendarDetail);
        tvAvoid.setText(avoid);
        tvSuit.setText(suit);

        //开启呼吸动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_breathe);
        animation.setInterpolator(new AccelerateInterpolator());
        viewAnimBreathe.setAnimation(animation);
        animation.start();
    }

    /**
     * 加载标语
     */
    private void findSlogan() {
        APIService apiService = RetrofitManager.getClient().create(APIService.class);
        Call<SloganBean> call = apiService.findSlogan(APIManager.URL_SLOGAN);
        call.enqueue(new Callback<SloganBean>() {
            @Override
            public void onResponse(Call<SloganBean> call, final Response<SloganBean> response) {
                if (response.code() == 200) {
                    SloganBean body = response.body();
                    final String english = body.getContent();
                    final String chinese = body.getNote();
                    BaseApplication.getMainHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            if (!TextUtils.isEmpty(english) && !TextUtils.isEmpty(chinese)) {
                                tvSloganEN.setText(english);
                                tvSloganCN.setText(chinese);
                                SPHelper.getInstance().put(SPKey.KEY_SLOGAN_EN, english);
                                SPHelper.getInstance().put(SPKey.KEY_SLOGAN_CN, chinese);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<SloganBean> call, Throwable t) {
                ErrHelper.check(t);
            }
        });
        addTaskToList(call);
    }

    /**
     * 获取指定日期的节假日及万年历信息
     */
    private void findCalendar() {
        APIService apiService = RetrofitManager.getClient(APIManager.getMxnzpUrl()).create(APIService.class);
        Call<CalendarBean> call = apiService.findCalendar(DateUtil.formatDateTime("yyyyMMdd"));
        call.enqueue(new Callback<CalendarBean>() {
            @Override
            public void onResponse(Call<CalendarBean> call, Response<CalendarBean> response) {
                if (200 != response.code()) return;
                CalendarBean.DataBean data = response.body().getData();
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

                tvLunar.setText(lunarCalendar);
                tvTypeDes.setText(typeDes);
                tvDetail.setText(detail);
                tvAvoid.setText(avoid);
                tvSuit.setText(suit);

                SPHelper.getInstance()
                        .put(SPKey.KEY_SUIT, suit)
                        .put(SPKey.KEY_AVOID, avoid)
                        .put(SPKey.KEY_LUNAR, lunarCalendar)
                        .put(SPKey.KEY_TYPE_DES, typeDes)
                        .put(SPKey.KEY_CALENDAR_DETAIL, detail)
                        .commit();
            }

            @Override
            public void onFailure(Call<CalendarBean> call, Throwable t) {
                ErrHelper.check(t);
            }
        });
        addTaskToList(call);
    }

    @Override
    public boolean showToolbar() {
        return false;
    }

    @OnClick({R.id.menu_calendar, R.id.menu_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_calendar:
                startActivity(new Intent(mContext, HistoryListActivity.class));
                break;
            case R.id.menu_setting:
                startActivity(new Intent(mContext, AboutActivity.class));
                break;
        }
    }
}
