package com.notonly.calendar.base;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.notonly.calendar.R;
import com.notonly.calendar.base.manager.AppManager;
import com.notonly.calendar.base.manager.PermissionManager;
import com.notonly.calendar.domain.UpdateBean;
import com.notonly.calendar.util.AppUtil;
import com.notonly.calendar.util.PathUtil;
import com.notonly.calendar.util.T;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import org.xutils.common.Callback;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity基类
 * Created by wangzhen on 16/2/23.
 */
public class BaseActivity extends AppCompatActivity implements PermissionManager.OnBaseCallback {

    public Context mContext;

    //任务请求队列
    private List<Callback.Cancelable> mListTasks;

    private PermissionManager.OnPermissionCallback mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.get().addActivity(this);
        mContext = this;
        mListTasks = new ArrayList<>();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (canNavigationBack()) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            //去除Toolbar下阴影
            actionBar.setElevation(0);
        }
    }

    protected boolean canNavigationBack() {
        return true;
    }

    /**
     * 检测更新
     */
    public void checkUpdate() {
        PgyUpdateManager.register(this, new UpdateManagerListener() {
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
                        String currentCode = AppUtil.getVersionCode(mContext);
                        String serverCode = data.getVersionCode();
                        if (serverCode.compareTo(currentCode) > 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("版本更新 V" + data.getVersionName());
                            builder.setMessage(data.getReleaseNote());
                            builder.setNegativeButton("取消", null);
                            builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    PermissionManager.requestPermission(BaseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionManager.OnPermissionCallback() {
                                        @Override
                                        public void onGranted() {
                                            download(data.getDownloadURL());
                                        }

                                        @Override
                                        public void onDenied() {
                                            T.get(mContext).toast(getString(R.string.error_permission_denied));
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
        final String localUrl = PathUtil.getDownloadPath() + "/" + AppUtil.getAppName(mContext) + ".apk";
        File path = new File(localUrl);
        if (path.exists()) {
            path.delete();
        }
        final DownloadManager dManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        // 设置下载路径和文件名
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, AppUtil.getAppName(mContext) + ".apk");
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
                    T.get(mContext).toast(AppUtil.getAppName(mContext) + "下载完成");
//                    Intent install = new Intent(Intent.ACTION_VIEW);
//                    install.setDataAndType(Uri.fromFile(new File(localUrl)), "application/vnd.android.package-archive");
//                    install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(install);
                }
            }
        };
        mContext.registerReceiver(receiver, filter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
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


    /**
     * 设置状态栏模式: 正常/亮色
     *
     * @param textDark 是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public final boolean setStatusBarMode(boolean textDark) {

        if (setFlymeStatusBarLightMode(getWindow(), textDark)
                | setMIUIStatusBarLightMode(getWindow(), textDark)) {
            return true;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int visibility = textDark
                    ? (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                    : View.SYSTEM_UI_FLAG_VISIBLE;
            getWindow().getDecorView().setSystemUiVisibility(visibility);
            return true;
        }

        return false;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean setFlymeStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null); // 获取的静态字段
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean setMIUIStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppManager.get().removeActivity(this);
        //取消所有任务
        for (Callback.Cancelable task : mListTasks) {
            if (!task.isCancelled()) {
                task.cancel();
            }
        }
        mListTasks.clear();
    }

}
