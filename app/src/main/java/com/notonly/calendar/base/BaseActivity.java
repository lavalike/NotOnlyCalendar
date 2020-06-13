package com.notonly.calendar.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.notonly.calendar.base.manager.AppManager;
import com.notonly.calendar.base.manager.PermissionManager;
import com.notonly.calendar.base.toolbar.ToolbarActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Activity基类
 * Created by wangzhen on 16/2/23.
 */
public class BaseActivity extends ToolbarActivity implements PermissionManager.OnBaseCallback {

    public Context mContext;

    //任务请求队列
    private List<Call> mListTasks;

    private PermissionManager.OnPermissionCallback mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.get().addActivity(this);
        mContext = this;
        mListTasks = new ArrayList<>();
    }

    /**
     * 将任务添加到队列
     */
    public void addTaskToList(Call task) {
        if (mListTasks == null) {
            mListTasks = new ArrayList<>();
        }
        mListTasks.add(task);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限被授予
                if (mCallback != null) {
                    mCallback.onGranted();
                }
            } else {
                //权限被拒绝
                if (mCallback != null) {
                    mCallback.onDenied();
                }
            }

        }
    }

    @Override
    public void setPermissionCallback(PermissionManager.OnPermissionCallback callback) {
        mCallback = callback;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppManager.get().removeActivity(this);
        //取消所有任务
        for (Call task : mListTasks) {
            if (!task.isCanceled()) {
                task.cancel();
            }
        }
        mListTasks.clear();
    }

}
