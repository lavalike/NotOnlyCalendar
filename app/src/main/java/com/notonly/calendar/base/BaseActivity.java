package com.notonly.calendar.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.notonly.calendar.base.manager.AppManager;
import com.notonly.calendar.base.manager.PermissionManager;
import com.notonly.calendar.base.toolbar.ToolbarActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

    /**
     * 设置状态栏模式: 正常/亮色
     *
     * @param textDark 是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public final boolean setDarkStatusBar(boolean textDark) {
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
        for (Call task : mListTasks) {
            if (!task.isCanceled()) {
                task.cancel();
            }
        }
        mListTasks.clear();
    }

}
