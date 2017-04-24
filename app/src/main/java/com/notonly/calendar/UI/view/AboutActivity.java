package com.notonly.calendar.UI.view;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.notonly.calendar.R;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.base.manager.APIManager;
import com.notonly.calendar.base.toolbar.ToolBarCommonHolder;
import com.notonly.calendar.util.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 关于页面
 * created by wangzhen on 2016/11/18
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.iv_qrcode)
    ImageView mImageView;
    @BindView(R.id.tv_version)
    TextView mTextViewVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        Glide.with(mContext).load(APIManager.URL_WEIXIN_QRCODE).placeholder(R.mipmap.qrcode).into(mImageView);
        mTextViewVersion.setText("版本：" + AppUtils.getVersionName(mContext));
    }

    @Override
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        new ToolBarCommonHolder(this, toolbar, getString(R.string.title_about));
    }
}
