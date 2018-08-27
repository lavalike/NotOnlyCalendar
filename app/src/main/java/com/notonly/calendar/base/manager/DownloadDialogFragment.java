package com.notonly.calendar.base.manager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.notonly.calendar.R;
import com.notonly.calendar.util.DownloadUtil;
import com.notonly.calendar.util.T;
import com.notonly.calendar.util.UIUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 显示进度的文件下载对话框
 * Created by wangzhen on 2017/6/30.
 */
public class DownloadDialogFragment extends DialogFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    private String url;
    private String MIME_APK = "application/vnd.android.package-archive";
    private Context context;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_progress_download_layout, container, false);
        ButterKnife.bind(this, view);
        context = UIUtils.getContext();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        download();
    }

    private void download() {
        DownloadUtil.get()
                .setListener(new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onLoading(int progress) {
                        updateMessage(progress);
                    }

                    @Override
                    public void onSuccess(String path) {
                        if (!path.endsWith(".apk")) {
                            T.get(context).toast(getString(R.string.error_invalid_apk_file));
                            dismissAllowingStateLoss();
                            return;
                        }
                        final File file = new File(path);
                        if (file.exists()) {
                            tvTitle.setText(getString(R.string.tip_update_complete));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent install = new Intent(Intent.ACTION_VIEW);
                                    install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    //兼容7.0私有文件权限
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
                                        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        install.setDataAndType(apkUri, MIME_APK);
                                    } else {
                                        install.setDataAndType(Uri.fromFile(file), MIME_APK);
                                    }
                                    context.startActivity(install);
                                    dismissAllowingStateLoss();
                                }
                            }, 1000);
                        }
                    }

                    @Override
                    public void onFail(String err) {
                        T.get(context).toast(err);
                        dismissAllowingStateLoss();
                    }
                })
                .setFileName("NotOnlyCalendar.apk")
                .download(this.url);
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title))
            tvTitle.setText(title);
    }

    /**
     * 更新进度信息
     *
     * @param progress 进度
     */
    private void updateMessage(int progress) {
        if (progress < 0) progress = 0;
        if (progress > 100) progress = 100;
        progressBar.setProgress(progress);
        tvProgress.setText(progress + "%");
    }

    /**
     * 设置下载url
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void onStart() {
        super.onStart();
        initWindow();
    }

    private void initWindow() {
        if (getDialog() == null) return;
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams attr = window.getAttributes();
            attr.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(attr);
        }
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);
    }
}
