package com.notonly.calendar.ui.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wangzhen.commons.toolbar.impl.Toolbar;
import com.notonly.calendar.R;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.base.toolbar.AppCommonToolbar;
import com.notonly.calendar.util.AppUtils;
import com.notonly.calendar.util.WxUtils;

import org.jetbrains.annotations.Nullable;

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

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return new AppCommonToolbar(this, getString(R.string.title_about));
    }

    @OnClick(R.id.btn_jump)
    public void onViewClicked(View view) {
        WxUtils.openMiniProgram(this);
    }
}
