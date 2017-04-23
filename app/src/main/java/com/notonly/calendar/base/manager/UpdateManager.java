package com.notonly.calendar.base.manager;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;

import com.google.gson.Gson;
import com.notonly.calendar.R;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.domain.UpdateBean;
import com.notonly.calendar.util.AppUtils;
import com.notonly.calendar.util.PathUtil;
import com.notonly.calendar.util.T;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import java.io.File;

/**
 * 更新检测
 * Created by wangzhen on 2017/3/8.
 */

public class UpdateManager {
    private static BaseActivity mContext;
    private static UpdateManager mInstance;

    public UpdateManager(BaseActivity activity) {
        mContext = activity;
    }

    public static UpdateManager get(BaseActivity activity) {
        if (mInstance == null) {
            synchronized (UpdateManager.class) {
                if (mInstance == null) {
                    mInstance = new UpdateManager(activity);
                }
            }
        }
        return mInstance;
    }

    public void check() {
        if (mContext == null) {
            throw new NullPointerException("Context must not be null.");
        }
        PgyUpdateManager.register(mContext, new UpdateManagerListener() {
            @Override
            public void onNoUpdateAvailable() {

            }

            @Override
            public void onUpdateAvailable(String s) {
                Gson gson = new Gson();
                UpdateBean updateBean = gson.fromJson(s, UpdateBean.class);
                if (updateBean != null) {
                    final UpdateBean.DataBean data = updateBean.getData();
                    if (data != null) {
                        String currentCode = AppUtils.getVersionCode(mContext);
                        String serverCode = data.getVersionCode();
                        if (serverCode.compareTo(currentCode) > 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("版本更新 V" + data.getVersionName());
                            builder.setMessage(data.getReleaseNote());
                            builder.setNegativeButton("取消", null);
                            builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    PermissionManager.requestPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionManager.OnPermissionCallback() {
                                        @Override
                                        public void onGranted() {
                                            download(data.getDownloadURL());
                                        }

                                        @Override
                                        public void onDenied() {
                                            T.get(mContext).toast(mContext.getString(R.string.error_permission_denied));
                                            PermissionManager.managePermissionByHand(mContext);
                                        }
                                    });
                                }
                            });
                            builder.setCancelable(false);
                            builder.show();
                        }
                    }
                }
            }
        });
    }

    /**
     * 下载更新
     *
     * @param url
     */
    private void download(String url) {
        final String localUrl = PathUtil.getDownloadPath() + "/" + AppUtils.getAppName(mContext) + ".apk";
        File path = new File(localUrl);
        if (path.exists()) {
            path.delete();
        }
        final DownloadManager dManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        // 设置下载路径和文件名
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, AppUtils.getAppName(mContext) + ".apk");
        request.setDescription("正在更新");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setMimeType("application/vnd.android.package-archive");
        // 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
        // 设置为可见和可管理
        request.setVisibleInDownloadsUi(true);
        // 获取此次下载的ID
        final long refernece = dManager.enqueue(request);
        // 注册广播接收器，当下载完成时自动安装
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        BroadcastReceiver receiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (refernece == myDwonloadID) {
                    T.get(mContext).toast(AppUtils.getAppName(mContext) + "下载完成，请点击安装");
//                    Intent install = new Intent(Intent.ACTION_VIEW);
//                    install.setDataAndType(Uri.fromFile(new File(localUrl)), "application/vnd.android.package-archive");
//                    install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(install);
                }
            }
        };
        mContext.registerReceiver(receiver, filter);
    }
}
