package com.notonly.calendar.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.notonly.calendar.base.manager.PermissionManager;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment基类
 * Created by wangzhen on 16/3/4.
 */
public abstract class BaseFragment extends Fragment implements PermissionManager.OnBaseCallback {

    protected Context mContext;
    //任务请求队列
    private List<Callback.Cancelable> mListTasks;
    private PermissionManager.OnPermissionCallback mCallback;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        mListTasks = new ArrayList<>();
    }

    /**
     * 将任务添加到队列
     */
    public void addTaskToList(Callback.Cancelable task) {
        if (mListTasks == null) {
            mListTasks = new ArrayList<>();
        }
        mListTasks.add(task);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
    public void onDestroy() {
        super.onDestroy();
        //取消所有任务
        for (Callback.Cancelable task : mListTasks) {
            if (!task.isCancelled()) {
                task.cancel();
            }
        }
        mListTasks.clear();
    }
}
