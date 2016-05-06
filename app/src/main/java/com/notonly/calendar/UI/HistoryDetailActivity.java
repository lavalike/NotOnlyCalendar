package com.notonly.calendar.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.notonly.calendar.R;
import com.notonly.calendar.bean.HistoryBean;
import com.notonly.calendar.bean.HistoryDetailBean;
import com.notonly.calendar.util.App;
import com.notonly.calendar.util.Constants;
import com.notonly.calendar.util.NetworkUtil;
import com.notonly.calendar.util.ToastUtil;
import com.notonly.calendar.view.ShareDialog;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史上的今天详情
 * wangzhen 2016/5/6
 */
@ContentView(R.layout.activity_history_detail)
public class HistoryDetailActivity extends AppCompatActivity {

    private Context mContext;
    @ViewInject(R.id.toolbar_collapse)
    private Toolbar mToolBar;
    @ViewInject(R.id.sr_history_detail)
    private SwipeRefreshLayout mSwipeRefresh;
    @ViewInject(R.id.ImageView_header)
    private ImageView mHeaderImage;
    @ViewInject(R.id.tv_history_detail)
    private TextView mTextViewContent;
    /**
     * 条目ID
     */
    private String mID;
    private List<Callback.Cancelable> mTasks;
    private IWXAPI wxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mContext = this;
        App.getInstance().addActivity(this);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupWeixin();
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        HistoryBean bean = (HistoryBean) intent.getSerializableExtra("data");
        if (bean == null) {
            return;
        }
        mTasks = new ArrayList<>();
        mID = bean.getId();
        String title = bean.getTitle();
        getSupportActionBar().setTitle(title);
        //设置加载图标颜色
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_purple, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(true);
            }
        });
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDetail();
            }
        });
        loadDetail();
    }

    /**
     * 加载详情
     */
    private void loadDetail() {
        RequestParams params = new RequestParams(Constants.url_historydetail);
        params.addQueryStringParameter("key", Constants.AppKey_todayinhistory);
        params.addQueryStringParameter("v", "1.0");
        params.addQueryStringParameter("id", mID);
        Callback.Cancelable task = x.http().get(params, new Callback.CommonCallback<String>() {
            String result = "";
            boolean hasErr = false;

            @Override
            public void onSuccess(String result) {
                this.result = result;
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                this.hasErr = true;
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
                if (this.result.equals("") || this.hasErr)
                    return;
                try {
                    JSONObject object = new JSONObject(this.result);
                    Gson gson = new Gson();
                    HistoryDetailBean bean = gson.fromJson(object.getString("result"), HistoryDetailBean.class);
                    if (bean == null) {
                        return;
                    }
                    displayData(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        mTasks.add(task);
    }

    /**
     * 展示数据
     *
     * @param bean
     */
    private void displayData(HistoryDetailBean bean) {
        String imgUrl = bean.getPic();
        String content = bean.getContent();
        if (!imgUrl.equals("")) {
            x.image().bind(mHeaderImage, imgUrl);
        }
        mTextViewContent.setText(content);
    }

    /**
     * 设置微信
     */
    private void setupWeixin() {
        wxapi = WXAPIFactory.createWXAPI(mContext, Constants.AppID_WX);
        wxapi.registerApp(Constants.AppID_WX);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                final String content = mTextViewContent.getText().toString().trim();
                if (!content.equals("")) {
                    ShareDialog dialog = new ShareDialog(mContext, new ShareDialog.ShareDialogListener() {
                        @Override
                        public void onWXSceneSelected() {
                            share2Weixin(0, content);
                        }

                        @Override
                        public void onWXTimelineSelected() {
                            share2Weixin(1, content);
                        }
                    });
                    dialog.show();
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 分享到微信
     *
     * @param type 0会话/1朋友圈
     * @param data
     */
    private void share2Weixin(int type, String data) {
        //创建一个用于封装文本的WXTextObject对象
        WXTextObject textObject = new WXTextObject();
        textObject.text = data;
        //创建一个WXMediaObject对象，用于向微信发送数据
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = data;
        //创建一个用于请求微信客户端的SendMessageWX.Req对象
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.transaction = buildTransaction("text");
        req.scene = (type == 0) ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        wxapi.sendReq(req);
    }

    //为请求生成一个唯一标识
    private String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTasks != null) {
            for (Callback.Cancelable task :
                    mTasks) {
                if (!task.isCancelled()) {
                    task.cancel();
                }
            }
            mTasks.clear();
        }
    }
}
