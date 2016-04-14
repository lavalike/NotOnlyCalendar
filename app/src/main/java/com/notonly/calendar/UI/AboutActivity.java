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
public class AboutActivity extends BaseActivity {

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
        options = new ImageOptions.Builder().setLoadingDrawableId(R.drawable.qrcode).setFailureDrawableId(R.drawable.qrcode).build();
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
                        SaveFile();
                    }
                }).show();
                break;
            default:
                break;
        }

    }

    /**
     * 保存文件
     */
    private void SaveFile() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ToastUtil.getInstance(mContext).toast("存储卡未就绪");
            return;
        }
        File path = new File(Environment.getExternalStorageDirectory() + "/NotOnlyCalendar/Images/");
        if (!path.exists()) {
            path.mkdirs();
        }
        RequestParams params = new RequestParams(Constants.url_weixin_qrcode);
        params.setSaveFilePath(path.getAbsolutePath() + "/qrcode.jpg");

        x.http().get(params, new Callback.CommonCallback<File>() {
            boolean hasErr = false;

            @Override
            public void onSuccess(File result) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                hasErr = true;
                ToastUtil.getInstance(mContext).toast("请检查网络");
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
    }
}
