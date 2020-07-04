package com.notonly.calendar.user_interface.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.notonly.calendar.R;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.base.helper.APIKey;
import com.notonly.calendar.base.toolbar.ToolBarCommonHolder;
import com.notonly.calendar.util.AppUtils;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于页面
 * created by wangzhen on 2016/11/18
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.tv_version)
    TextView mTextViewVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        mTextViewVersion.setText(String.format("版本：%s", AppUtils.getVersionName(mContext)));
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.title_about));
    }


    @OnClick(R.id.btn_jump)
    public void onViewClicked(View view) {
        IWXAPI api = WXAPIFactory.createWXAPI(this, APIKey.AppID_WX);
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = APIKey.MINI_APP_ID;
        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;
        api.sendReq(req);
    }
}
