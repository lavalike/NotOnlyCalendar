package com.notonly.calendar.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.notonly.calendar.R;
import com.notonly.calendar.util.App;
import com.notonly.calendar.util.Constants;
import com.notonly.calendar.util.NetworkUtil;
import com.notonly.calendar.util.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

@ContentView(R.layout.activity_about)
public class AboutActivity1 extends BaseActivity {

    private Context mContext;
    private ImageOptions options;
    @ViewInject(R.id.iv_qrcode)
    private ImageView mImageView;
    @ViewInject(R.id.tv_version)
    private TextView mTextViewVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        App.getInstance().addActivity(this);
        options = new ImageOptions.Builder().setLoadingDrawableId(R.mipmap.qrcode).setFailureDrawableId(R.mipmap.qrcode).build();
        x.image().bind(mImageView, Constants.url_weixin_qrcode, options);
        mTextViewVersion.setText("版本：" + NetworkUtil.getAppVersion(mContext));
    }

    @Event(value = R.id.iv_qrcode)
    private void imageClick(View view) {
        switch (view.getId()) {
            case R.id.iv_qrcode:
                new AlertDialog.Builder(mContext).setItems(new String[]{"保存"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveQrcode2File();
                    }
                }).show();
                break;
            default:
                break;
        }

    }

    /**
     * 二维码保存为文件
     */
    private void saveQrcode2File() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ToastUtil.getInstance(mContext).toast(getString(R.string.error_sdcard_failure));
            return;
        }
        File path = new File(Environment.getExternalStorageDirectory() + "/NotOnlyCalendar/Images/");
        if (!path.exists()) {
            path.mkdirs();
        }
        RequestParams params = new RequestParams(Constants.url_weixin_qrcode);
        params.setSaveFilePath(path.getAbsolutePath() + "/qrcode.jpg");

        Callback.Cancelable task = x.http().get(params, new Callback.CommonCallback<File>() {
            boolean hasErr = false;

            @Override
            public void onSuccess(File result) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                hasErr = true;
                if (!NetworkUtil.isNetworkAvailable(mContext)) {
                    ToastUtil.getInstance(mContext).toast(getString(R.string.error_network));
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                if (!hasErr) {
                    ToastUtil.getInstance(mContext).toast("已保存");
                }
            }
        });
        addTaskToList(task);
    }
}
