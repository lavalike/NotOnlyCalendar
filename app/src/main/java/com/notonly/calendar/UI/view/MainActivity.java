package com.notonly.calendar.UI.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.notonly.calendar.R;
import com.notonly.calendar.api.APIService;
import com.notonly.calendar.base.App;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.base.helper.APIKey;
import com.notonly.calendar.base.helper.ErrHelper;
import com.notonly.calendar.base.helper.SPHelper;
import com.notonly.calendar.base.helper.SPKey;
import com.notonly.calendar.base.manager.APIManager;
import com.notonly.calendar.base.retrofit.RetrofitManager;
import com.notonly.calendar.domain.CalendarBean;
import com.notonly.calendar.domain.Device;
import com.notonly.calendar.domain.SloganBean;
import com.notonly.calendar.util.AppUtils;
import com.notonly.calendar.util.DateUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
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
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_avoid)
    TextView tvAvoid;
    @BindView(R.id.tv_suit)
    TextView tvSuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Bmob.initialize(mContext, APIKey.AppKey_bmob);
        collectDeviceData();
        init();
        findSlogan();
        findCalendar();
    }

    private void findCalendar() {
        APIService apiService = RetrofitManager.getClient().create(APIService.class);
        Call<CalendarBean> call = apiService.findCalendar(DateUtil.getDatetime(), APIKey.AppKey_Calendar);
        call.enqueue(new Callback<CalendarBean>() {
            @Override
            public void onResponse(Call<CalendarBean> call, Response<CalendarBean> response) {
                if (!response.isSuccessful()) return;
                final CalendarBean bean = response.body();
                final CalendarBean.ResultBean result = bean.getResult();
                if (result == null) return;
                final CalendarBean.ResultBean.DataBean data = result.getData();
                App.getMainHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        tvAvoid.setText(data.getAvoid());
                        tvSuit.setText(data.getSuit());
                    }
                });
            }

            @Override
            public void onFailure(Call<CalendarBean> call, Throwable t) {
                ErrHelper.check(t);
            }
        });
        addTaskToList(call);
    }

    private void init() {
        tvDay.setText(DateUtil.getDay());
    }

    /**
     * 加载标语
     */
    private void findSlogan() {
        String sloganCN = SPHelper.getInstance().get(SPKey.KEY_SLOGAN_CN, "");
        String sloganEN = SPHelper.getInstance().get(SPKey.KEY_SLOGAN_EN, "");
        tvSloganCN.setText(sloganCN);
        tvSloganEN.setText(sloganEN);
        APIService apiService = RetrofitManager.getClient().create(APIService.class);
        Call<SloganBean> call = apiService.findSlogan(APIManager.URL_SLOGAN);
        call.enqueue(new Callback<SloganBean>() {
            @Override
            public void onResponse(Call<SloganBean> call, final Response<SloganBean> response) {
                SloganBean body = response.body();
                String en = body.getContent();
                String cn = body.getNote();
                en = en.substring(0, en.lastIndexOf(".") + 1);
                cn = cn.substring(0, cn.lastIndexOf("。") + 1);
                final String finalEn = en;
                final String finalCn = cn;
                App.getMainHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(finalEn) && !TextUtils.isEmpty(finalCn)) {
                            tvSloganEN.setText(finalEn);
                            tvSloganCN.setText(finalCn);
                            SPHelper.getInstance().put(SPKey.KEY_SLOGAN_EN, finalEn);
                            SPHelper.getInstance().put(SPKey.KEY_SLOGAN_CN, finalCn);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<SloganBean> call, Throwable t) {
                ErrHelper.check(t);
            }
        });
        addTaskToList(call);
    }

    @Override
    public boolean showToolbar() {
        return false;
    }

    /**
     * 收集设备信息
     */
    private void collectDeviceData() {
        TelephonyManager manager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        final String deviceID = manager.getDeviceId();//设备ID
        final String mobileNumber = manager.getLine1Number();//手机号
        final String appVersion = AppUtils.getVersionName(mContext);
        final String sysVersion = Build.VERSION.RELEASE;
        final String brand = Build.BRAND;
        final String model = Build.MODEL;

        BmobQuery<Device> query = new BmobQuery<>();
        query.addWhereEqualTo("DeviceID", deviceID);
        query.findObjects(mContext, new FindListener<Device>() {
            @Override
            public void onSuccess(List<Device> list) {
                //提交手机信息
                Device bean = new Device();
                bean.setAppVersion(appVersion);
                bean.setBrand(brand);
                bean.setDeviceID(deviceID);
                bean.setModel(model);
                bean.setSysVersion(sysVersion);
                bean.setMobileNumber(mobileNumber);
                if (list.size() == 0) {
                    bean.save(mContext, new SaveListener() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                } else {
                    bean.update(mContext, list.get(0).getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {
            }
        });
    }

    @OnClick(R.id.menu_calendar)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_calendar:
                startActivity(new Intent(mContext, HistoryListActivity.class));
                break;
        }
    }
}
