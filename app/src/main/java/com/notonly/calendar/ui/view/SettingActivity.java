package com.notonly.calendar.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dimeno.commons.toolbar.impl.Toolbar;
import com.notonly.calendar.R;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.base.toolbar.AppCommonToolbar;

import org.jetbrains.annotations.Nullable;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * SettingActivity
 * Created by wangzhen on 2020/9/4.
 */
public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.container_about, R.id.container_privacy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.container_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.container_privacy:
                startActivity(new Intent(this, BrowserActivity.class).putExtra("url", "https://www.jianshu.com/p/58f96dd3a365").putExtra("title", "隐私政策"));
                break;
        }
    }

    @Nullable
    @Override
    public Toolbar createToolbar() {
        return new AppCommonToolbar(this, getString(R.string.title_setting));
    }
}