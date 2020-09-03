package com.notonly.calendar.base.toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.notonly.calendar.R;

/**
 * 处理Toolbar相关
 * Created by Administrator on 2016/10/18.
 */
public class ToolbarActivity extends AppCompatActivity {

    private FrameLayout mRootView;

    @CallSuper
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        ((ViewGroup) getWindow().getDecorView()).getChildAt(0).setFitsSystemWindows(false);
        View view = findViewById(R.id.action_bar_root);
        if (view != null) {
            view.setFitsSystemWindows(false);
        }
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(getLayoutInflater().inflate(layoutResID, null), null);
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, null);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (showToolbar()) {
            initContentView(view);
            initToolbar();
        } else {
            super.setContentView(view);
        }
    }

    /**
     * 创建帧布局，作为视图容器的父容器
     *
     * @param view view
     */
    private void initContentView(View view) {
        mRootView = new FrameLayout(this);
        mRootView.setFitsSystemWindows(true);
        mRootView.setBackgroundResource(R.color.colorPrimary);

        if (view != null && view.getBackground() == null) {
            view.setBackgroundColor(Color.parseColor("#f2f2f2"));
        }

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        params.topMargin = (int) getResources().getDimension(R.dimen.toolbar_height);
        mRootView.addView(view, params);
        super.setContentView(mRootView);
    }

    private void initToolbar() {
        View toolbarView = LayoutInflater.from(this).inflate(R.layout.toolbar_layout, mRootView);
        Toolbar toolbar = (Toolbar) toolbarView.findViewById(R.id.id_tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        onSetupToolbar(toolbar, actionBar);
    }

    /**
     * 设置Toolbar的数据，也可以设置自定义toolBar视图 如需则交给子类去实现
     *
     * @param toolbar   toolbar
     * @param actionBar 为了兼容性，设置title、subTitle等使用actionBar
     */
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        // 注意：此段代码在子类的onCreate()方法的setContentView(layoutResId)中执行
        // 特别注意其他View与toolBar的交互
    }

    /**
     * 是否显示Toolbar
     *
     * @return bolean
     */
    public boolean showToolbar() {
        return true;
    }
}
