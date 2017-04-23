package com.notonly.calendar.base.toolbar;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.View;


/**
 * 自定义Toolbar基类
 * Created by wangzhen on 16/10/19.
 */

public abstract class BaseToolBarHolder implements View.OnClickListener {

    protected Activity mActivity;
    protected Toolbar mToolbar;

    private OnClickListener mOnClickListener;

    public BaseToolBarHolder(final Activity activity, Toolbar toolbar) {
        this.mActivity = activity;
        this.mToolbar = toolbar;
        toolbar.showOverflowMenu();
        toolbar.setContentInsetsRelative(0, 0);
        View.inflate(mActivity, getToolBarLayoutResId(), mToolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivity != null) {
                    mActivity.finish();
                }
            }
        });
        initView();
    }

    /**
     * 设置ToolBar所有可点击控件的点击监听
     *
     * @param onClickListener
     */
    public void setAllOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    /**
     * see Activity.findViewById()
     */
    public <T extends View> T findById(int id) {
        return (T) mToolbar.findViewById(id);
    }

    protected abstract void initView();

    /**
     * 获取自定义ToolBar视图的资源id
     *
     * @return
     */
    protected abstract int getToolBarLayoutResId();

    /**
     * 获取右边自定义菜单
     *
     * @param <T>
     * @return
     */
    protected abstract <T extends View> T getRightMenu();


    @Override
    public void onClick(View v) {
        if (null != mOnClickListener) {
            mOnClickListener.onClick(v);
        }
    }

    /**
     * 点击回调接口
     */
    public interface OnClickListener {
        void onClick(View v);
    }

}