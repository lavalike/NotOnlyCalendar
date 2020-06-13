package com.notonly.calendar.user_interface.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.notonly.calendar.R;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.base.manager.APIManager;
import com.notonly.calendar.base.toolbar.ToolBarCommonHolder;
import com.notonly.calendar.util.AppUtils;
import com.notonly.calendar.util.DownloadUtil;
import com.notonly.calendar.util.PathUtil;
import com.notonly.calendar.util.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;

/**
 * 关于页面
 * created by wangzhen on 2016/11/18
 */
public class AboutActivity extends BaseActivity implements DownloadUtil.OnDownloadListener {

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

    @OnLongClick(R.id.iv_qrcode)
    public boolean onLongClick(View view) {
        DownloadUtil.get()
                .setDir(PathUtil.getImagePath())
                .setFileName(System.currentTimeMillis() + ".png")
                .setListener(this)
                .download(APIManager.URL_WEIXIN_QRCODE);
        return true;
    }

    @Override
    public void onLoading(int progress) {

    }

    @Override
    public void onSuccess(String path) {
        T.get(this).toast("保存成功！" + path);
    }

    @Override
    public void onFail(String err) {
        T.get(this).toast(getString(R.string.error_common));
    }
}
