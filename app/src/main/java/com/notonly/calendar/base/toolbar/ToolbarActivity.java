package com.notonly.calendar.base.toolbar;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.notonly.calendar.R;

/**
 * 处理Toolbar相关
 * Created by Administrator on 2016/10/18.
 */

public class ToolbarActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private LayoutInflater mInflater;
    private FrameLayout mRootView;

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
//            initView(view);
//            super.setContentView(mRootView);
            super.setContentView(view);
        }
    }

    private void initView(View view) {
        mRootView = new FrameLayout(this);
        mRootView.setBackgroundResource(R.color.colorPrimaryDark);
        mRootView.setFitsSystemWindows(true);
        FrameLayout.LayoutParams paramsRoot = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mRootView.setLayoutParams(paramsRoot);
        Drawable background = view.getBackground();
        if (null == background) {
            view.setBackgroundColor(Color.parseColor("#f2f2f2"));
        }
        mRootView.addView(view);
    }

    private void initToolbar() {
        super.setContentView(mRootView);
        mInflater = LayoutInflater.from(this);
        View toolbarView = mInflater.inflate(R.layout.toolbar_layout, mRootView);
        mToolbar = (Toolbar) toolbarView.findViewById(R.id.id_tool_bar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        onSetupToolbar(mToolbar, actionBar);
    }

    /**
     * 设置Toolbar的数据，也可以设置自定义toolBar视图 如需则交给子类去实现
     *
     * @param toolbar
     * @param actionBar 为了兼容性，设置title、subTitle等使用actionBar
     */
    protected void onSetupToolbar(Toolbar toolbar, ActionBar actionBar) {
        // 注意：此段代码在子类的onCreate()方法的setContentView(layoutResId)中执行
        // 特别注意其他View与toolBar的交互
    }

    /**
     * 创建帧布局，作为视图容器的父容器
     *
     * @param view
     */
    private void initContentView(View view) {
        if (view != null && view.getParent() != null && view.getParent() instanceof ViewGroup) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        mRootView = new FrameLayout(this);
        mRootView.setBackgroundResource(R.color.colorPrimaryDark);
        mRootView.setFitsSystemWindows(true);
        FrameLayout.LayoutParams paramsRoot = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mRootView.setLayoutParams(paramsRoot);
        //设置视图顶部的Toolbar高度
        int toolbarSize = (int) getResources().getDimension(R.dimen.toolbar_height);
        FrameLayout.LayoutParams paramsView = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        paramsView.topMargin = toolbarSize;
        Drawable background = view.getBackground();
        if (null == background) {
            view.setBackgroundColor(Color.parseColor("#f2f2f2"));
        }
        //将视图添加到父容器
        mRootView.addView(view, paramsView);
    }

    /**
     * 是否显示Toolbar
     *
     * @return
     */
    public boolean showToolbar() {
        return true;
    }
}
