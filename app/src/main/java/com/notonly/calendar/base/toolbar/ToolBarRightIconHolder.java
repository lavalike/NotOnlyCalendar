package com.notonly.calendar.base.toolbar;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.notonly.calendar.R;

/**
 * 常用布局 ：左返回-标题中-右图标
 * Created by wangzhen on 16/10/19.
 */

public class ToolBarRightIconHolder extends BaseToolBarHolder {

    private TextView tv_title;
    private ImageView iv_menu;

    public ToolBarRightIconHolder(Activity activity, Toolbar toolbar, String title, int resId) {
        this(activity, toolbar, title, resId, true);
    }

    public ToolBarRightIconHolder(Activity activity, Toolbar toolbar, String title, int resId, boolean canBack) {
        super(activity, toolbar);
        tv_title.setText(title);
        iv_menu.setImageResource(resId);
        if (!canBack) {
            //去除返回箭头
            mToolbar.setNavigationIcon(null);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv_title.getLayoutParams();
            //方法一：去除TextView预留的右边距
            params.rightMargin = 0;
//            //方法二：为TextView左边添加同样的边距
//            params.leftMargin = (int) activity.getResources().getDimension(R.dimen.toolbar_height);
            tv_title.setLayoutParams(params);
        }
    }

    public ImageView getRightMenu() {
        return iv_menu;
    }

    @Override
    protected void initView() {
        tv_title = findById(R.id.tv_toolbar_right_icon_title);
        iv_menu = findById(R.id.iv_toolbar_right_icon_menu);
    }

    @Override
    protected int getToolBarLayoutResId() {
        return R.layout.layout_toolbar_right_icon;
    }

}
