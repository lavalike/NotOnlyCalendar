package com.notonly.calendar.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.notonly.calendar.R;
import com.notonly.calendar.bean.CalendarBean;
import com.notonly.calendar.bean.Device;
import com.notonly.calendar.util.App;
import com.notonly.calendar.util.Constants;
import com.notonly.calendar.util.DateUtil;
import com.notonly.calendar.util.NetworkUtil;
import com.notonly.calendar.util.ToastUtil;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Method;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private Context mContext;
    @ViewInject(R.id.SwipeRefresh1)
    private SwipeRefreshLayout mSwipeRefresh;
    @ViewInject(R.id.TextView_date)
    private TextView mTextView_date;
    @ViewInject(R.id.TextView_weekday)
    private TextView mTextView_weekday;
    @ViewInject(R.id.TextView_lunaryear)
    private TextView mTextView_lunaryear;
    @ViewInject(R.id.TextView_lunar)
    private TextView mTextView_lunar;
    @ViewInject(R.id.TextView_day)
    private TextView mTextView_day;
    @ViewInject(R.id.TextView_avoid)
    private TextView mTextView_avoid;
    @ViewInject(R.id.TextView_suit)
    private TextView mTextView_suit;

    private Callback.Cancelable task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mContext = this;
        App.getInstance().addActivity(this);
        Bmob.initialize(mContext, Constants.AppKey_bmob);
        mTextView_date.setText(DateUtil.getYear() + "年" + DateUtil.getMonth() + "月");
        mTextView_day.setText(DateUtil.getDay());
        //设置加载图标颜色
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_purple, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(true);
            }
        });

        requestData();
        collectDeviceData();
    }

    /**
     * 收集设备信息
     */
    private void collectDeviceData() {
        TelephonyManager manager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        final String deviceID = manager.getDeviceId();//设备ID
        final String mobileNumber = manager.getLine1Number();//手机号
        final String appVersion = NetworkUtil.getAppVersion(mContext);
        final String sysVersion = Build.BRAND;
        final String brand = Build.VERSION.RELEASE;
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

    /**
     * 请求数据
     */
    private void requestData() {
        RequestParams params = new RequestParams(Constants.url_calendar);
        params.addQueryStringParameter("date", DateUtil.getDatetime());
        params.addQueryStringParameter("key", Constants.AppKey_Calendar);
        task = x.http().get(params, new Callback.CommonCallback<String>() {

            String result = "";
            boolean hasError = false;

            @Override
            public void onSuccess(String result) {
                this.result = result;
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                this.hasError = true;
                if (!NetworkUtil.isNetworkAvailable(mContext)) {
                    ToastUtil.getInstance(mContext).toast(getString(R.string.error_network));
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                mSwipeRefresh.setRefreshing(false);
                if (this.result.equals("") || this.hasError) {
                    return;
                }
                try {
                    JSONObject object = new JSONObject(this.result);
                    String data = new JSONObject(object.getString("result")).getString("data");
                    Gson gson = new Gson();
                    CalendarBean bean = gson.fromJson(data, CalendarBean.class);
                    inflateData(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.getInstance(mContext).toast("出错啦");
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_todayinhistory:
                startActivity(new Intent(mContext, TodayInHistoryActivity.class));
                break;
            case R.id.action_about:
                startActivity(new Intent(mContext, AboutActivity.class));
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 展示网络数据
     *
     * @param bean
     */
    private void inflateData(CalendarBean bean) {
        mTextView_weekday.setText(bean.getWeekday());
        mTextView_lunaryear.setText(bean.getLunarYear());
        mTextView_lunar.setText(bean.getLunar());
        mTextView_avoid.setText(bean.getAvoid());
        mTextView_suit.setText(bean.getSuit());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (task != null) {
            if (!task.isCancelled()) {
                task.cancel();
            }
        }
        //保持后台运行
        moveTaskToBack(false);
    }
}
