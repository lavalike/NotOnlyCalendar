package com.notonly.calendar.UI.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.notonly.calendar.R;
import com.notonly.calendar.UI.widget.ShareDialog;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.base.helper.APIKey;
import com.notonly.calendar.base.helper.ErrHelper;
import com.notonly.calendar.base.manager.APIManager;
import com.notonly.calendar.domain.HistoryBean;
import com.notonly.calendar.domain.HistoryDetailBean;
import com.notonly.calendar.util.T;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 历史上的今天详情
 * wangzhen 2016/5/6
 */
public class HistoryDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar_collapse)
    Toolbar mToolBar;
    @BindView(R.id.sr_history_detail)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.ImageView_header)
    ImageView mHeaderImage;
    @BindView(R.id.tv_history_detail)
    TextView mTextViewContent;
    /**
     * 条目ID
     */
    private String mID;
    private IWXAPI wxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupWeixin();
        HistoryBean.ResultBean bean = (HistoryBean.ResultBean) getIntent().getSerializableExtra("data");
        if (bean == null) {
            return;
        }
        mID = bean.getE_id();
        String title = bean.getTitle();
        getSupportActionBar().setTitle(title);
        initSwipeRefresh();
        startLoading();
        loadDetail();
    }

    @Override
    public boolean showToolbar() {
        return false;
    }

    private void initSwipeRefresh() {
        //设置加载图标颜色
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_purple, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDetail();
            }
        });
    }

    public void startLoading() {
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(true);
            }
        });
    }

    public void stopLoading() {
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }

    /**
     * 加载详情
     */
    private void loadDetail() {
        RequestParams params = new RequestParams(APIManager.URL_HISTORY_DETAIL_V2);
        params.addQueryStringParameter("key", APIKey.AppKey_todayinhistory);
        params.addQueryStringParameter("e_id", mID);
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
                ErrHelper.check(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                stopLoading();
                if (this.result.equals("") || this.hasErr)
                    return;
                try {
                    Gson gson = new Gson();
                    HistoryDetailBean bean = gson.fromJson(this.result, HistoryDetailBean.class);
                    if (bean != null && bean.getResult() != null && bean.getResult().size() > 0) {
                        if (bean.getError_code() == 0) {
                            displayData(bean);
                        }
                    } else {
                        if (bean.getReason() != null) {
                            T.get(mContext).toast(bean.getReason());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        addTaskToList(task);
    }

    /**
     * 展示数据
     *
     * @param bean
     */
    private void displayData(HistoryDetailBean bean) {
        HistoryDetailBean.ResultBean result = bean.getResult().get(0);
        String content = result.getContent();
        mTextViewContent.setText(content);
        if (result.getPicUrl() != null && result.getPicUrl().size() > 0) {
            String imgUrl = result.getPicUrl().get(0).getUrl();
            if (!TextUtils.isEmpty(imgUrl)) {
                Glide.with(mContext).load(imgUrl).placeholder(R.mipmap.ic_header).into(mHeaderImage);
            }
        }
    }

    /**
     * 设置微信
     */
    private void setupWeixin() {
        wxapi = WXAPIFactory.createWXAPI(mContext, APIKey.AppID_WX);
        wxapi.registerApp(APIKey.AppID_WX);
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
}
