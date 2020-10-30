package com.notonly.calendar.base.toolbar;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.dimeno.commons.toolbar.impl.Toolbar;
import com.notonly.calendar.R;

import org.jetbrains.annotations.NotNull;

/**
 * app common toolbar
 * Created by wangzhen on 2020/10/30.
 */
public class AppCommonToolbar extends Toolbar {
    private final Activity activity;
    private final String title;
    private TextView tvTitle;

    public AppCommonToolbar(@NotNull Activity activity, String title) {
        super(activity);
        this.activity = activity;
        this.title = title;
    }

    @Override
    public int layoutRes() {
        return R.layout.toolbar_app_common_layout;
    }

    @Override
    public void onViewCreated(@NotNull View view) {
        super.onViewCreated(view);
        view.findViewById(R.id.back).setOnClickListener(v -> {
            activity.finish();
        });
        tvTitle = view.findViewById(R.id.title);
        tvTitle.setText(title);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }
}
