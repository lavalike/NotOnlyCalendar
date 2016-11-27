package com.notonly.calendar.UI.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.notonly.calendar.R;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.base.helper.APIKey;
import com.notonly.calendar.base.helper.ErrHelper;
import com.notonly.calendar.base.manager.APIManager;
import com.notonly.calendar.base.manager.PermissionManager;
import com.notonly.calendar.domain.CalendarBean;
import com.notonly.calendar.domain.Device;
import com.notonly.calendar.util.DateUtil;
import com.notonly.calendar.util.AppUtil;
import com.notonly.calendar.util.T;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 主页面
 * created by wangzhen on 2016/11/18
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.SwipeRefresh1)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.TextView_date)
    TextView mTextView_date;
    @BindView(R.id.TextView_weekday)
    TextView mTextView_weekday;
    @BindView(R.id.TextView_lunaryear)
    TextView mTextView_lunaryear;
    @BindView(R.id.TextView_lunar)
    TextView mTextView_lunar;
    @BindView(R.id.TextView_day)
    TextView mTextView_day;
    @BindView(R.id.TextView_avoid)
    TextView mTextView_avoid;
    @BindView(R.id.TextView_suit)
    TextView mTextView_suit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Bmob.initialize(mContext, APIKey.AppKey_bmob);
        initSwipe();
        mTextView_date.setText(DateUtil.getYear() + "年" + DateUtil.getMonth() + "月");
        mTextView_day.setText(DateUtil.getDay());
        requestData();
        checkUpdate();
        PermissionManager.requestPermission(this, Manifest.permission.READ_PHONE_STATE, new PermissionManager.OnPermissionCallback() {
            @Override
            public void onGranted() {
                collectDeviceData();
            }

            @Override
            public void onDenied() {
                T.get(mContext).toast(getString(R.string.error_permission_denied));
                PermissionManager.managePermissionByHand(mContext);
            }
        });
    }

    private void initSwipe() {
        //设置加载图标颜色
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_purple, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(true);
            }
        });
    }

    @Override
    protected boolean canNavigationBack() {
        return false;
    }

    /**
     * 收集设备信息
     */
    private void collectDeviceData() {
        TelephonyManager manager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        final String deviceID = manager.getDeviceId();//设备ID
        final String mobileNumber = manager.getLine1Number();//手机号
        final String appVersion = AppUtil.getVersionName(mContext);
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

    /**
     * 请求数据
     */
    private void requestData() {
        RequestParams params = new RequestParams(APIManager.URL_CALENDAR);
        params.addQueryStringParameter("date", DateUtil.getDatetime());
        params.addQueryStringParameter("key", APIKey.AppKey_Calendar);
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {

            String result = "";
            boolean hasError = false;

            @Override
            public void onSuccess(String result) {
                this.result = result;
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                this.hasError = true;
                ErrHelper.check(ex);
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
                    T.get(mContext).toast("出错啦");
                }

            }
        });
        addTaskToList(cancelable);
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
                startActivity(new Intent(mContext, HistoryListActivity.class));
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
    public void onDestroy() {
        super.onDestroy();
        //保持后台运行
        moveTaskToBack(false);
    }
}
