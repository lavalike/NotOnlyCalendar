package com.notonly.calendar.base.manager;

import android.Manifest;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;

import com.notonly.calendar.R;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.util.T;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;
import com.wangzhen.commons.utils.AppUtils;
import com.wangzhen.permission.PermissionManager;
import com.wangzhen.permission.callback.AbsPermissionCallback;

/**
 * 更新检测
 * Created by wangzhen on 2017/3/8.
 */

public class UpdateManager {
    private BaseActivity mContext;
    private String MIME_APK = "application/vnd.android.package-archive";

    private UpdateManager(BaseActivity activity) {
        mContext = activity;
    }

    public static UpdateManager get(BaseActivity activity) {
        return new UpdateManager(activity);
    }

    public void check() {
        if (mContext == null) {
            throw new NullPointerException("Context must not be null.");
        }
        PgyUpdateManager.register(new UpdateManagerListener() {

            @Override
            public void onNoUpdateAvailable() {

            }

            @Override
            public void onUpdateAvailable(final AppBean appBean) {
                if (appBean != null) {
                    String currentCode = String.valueOf(AppUtils.getVersionCode());
                    String serverCode = appBean.getVersionCode();
                    if (serverCode.compareTo(currentCode) > 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("版本更新 V" + appBean.getVersionName());
                        builder.setMessage(appBean.getReleaseNote());
                        builder.setNegativeButton("取消", null);
                        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PermissionManager.request(mContext, new AbsPermissionCallback() {
                                    @Override
                                    public void onGrant(String[] permissions) {
                                        download(appBean.getDownloadURL());
                                    }

                                    @Override
                                    public void onDeny(String[] deniedPermissions, String[] neverAskPermissions) {
                                        T.get(mContext).toast(mContext.getString(R.string.error_permission_denied));
                                    }
                                }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            }
                        });
                        builder.setCancelable(false);
                        builder.show();
                    }
                }
            }

            @Override
            public void checkUpdateFailed(Exception e) {

            }
        });
    }

    /**
     * 下载更新
     *
     * @param url
     */
    private void download(String url) {
        DownloadDialogFragment dialogFragment = new DownloadDialogFragment();
        dialogFragment.setUrl(url.trim());
        FragmentTransaction ft = mContext.getSupportFragmentManager().beginTransaction();
        ft.add(dialogFragment, DownloadDialogFragment.class.getSimpleName());
        ft.commitAllowingStateLoss();
    }
}
