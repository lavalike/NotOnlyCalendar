package com.notonly.calendar.UI.view;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.notonly.calendar.R;
import com.notonly.calendar.base.BaseActivity;
import com.notonly.calendar.base.helper.ErrHelper;
import com.notonly.calendar.base.manager.APIManager;
import com.notonly.calendar.base.manager.PermissionManager;
import com.notonly.calendar.util.AppUtil;
import com.notonly.calendar.util.PathUtil;
import com.notonly.calendar.util.T;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于页面
 * created by wangzhen on 2016/11/18
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.iv_qrcode)
    ImageView mImageView;
    @BindView(R.id.tv_version)
    TextView mTextViewVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        Glide.with(mContext).load(APIManager.url_weixin_qrcode).placeholder(R.mipmap.qrcode).into(mImageView);
        mTextViewVersion.setText("版本：" + AppUtil.getVersionName(mContext));
    }

    @OnClick(value = R.id.iv_qrcode)
    public void imageClick(View view) {
        switch (view.getId()) {
            case R.id.iv_qrcode:
                PermissionManager.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionManager.OnPermissionCallback() {
                    @Override
                    public void onGranted() {
                        new AlertDialog.Builder(mContext).setItems(new String[]{"保存"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                saveQrcode2File();
                            }
                        }).show();
                    }

                    @Override
                    public void onDenied() {
                        T.get(mContext).toast(getString(R.string.error_permission_denied));
                        PermissionManager.managePermissionByHand(mContext);
                    }
                });
                break;
            default:
                break;
        }

    }

    /**
     * 二维码保存为文件
     */
    private void saveQrcode2File() {
        RequestParams params = new RequestParams(APIManager.url_weixin_qrcode);
        params.setSaveFilePath(PathUtil.getImagePath() + File.separator + "qrcode.jpg");
        Callback.Cancelable task = x.http().get(params, new Callback.CommonCallback<File>() {
            boolean hasErr = false;

            @Override
            public void onSuccess(File result) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                hasErr = true;
                ErrHelper.check(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                if (!hasErr) {
                    T.get(mContext).toast("已保存");
                }
            }
        });
        addTaskToList(task);
    }
}
