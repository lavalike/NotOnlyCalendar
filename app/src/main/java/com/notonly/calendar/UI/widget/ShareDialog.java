package com.notonly.calendar.UI.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.notonly.calendar.R;

/**
 * 分享对话框
 * Created by wangzhen on 16/4/15.
 */
public class ShareDialog extends Dialog {

    private Context mContext;
    private ShareDialogListener mListener;

    public interface ShareDialogListener {
        /**
         * 分享到会话
         */
        void onWXSceneSelected();

        /**
         * 分享到朋友圈
         */
        void onWXTimelineSelected();
    }

    public ShareDialog(Context context, ShareDialogListener listener) {
        super(context, R.style.ShareDialog);
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_share_layout, null);
        setContentView(view);
        configDialog();
        ImageView imageViewWeixin = (ImageView) view.findViewById(R.id.iv_share_weixin);
        ImageView imageViewMoments = (ImageView) view.findViewById(R.id.iv_share_moments);
        TextView textViewCancel = (TextView) view.findViewById(R.id.tv_bottom_cancel);

        imageViewWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onWXSceneSelected();
                dismiss();
            }
        });
        imageViewMoments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onWXTimelineSelected();
                dismiss();
            }
        });
        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    /**
     * 配置对话框属性
     */
    private void configDialog() {
        //触摸外部可取消
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        //设置对话框位置：底部
        window.setGravity(Gravity.BOTTOM);
        // //dialog 默认的样式@android:style/Theme.Dialog 对应的style 有pading属性，所以setPadding(0, 0, 0, 0); 就能够水平占满了
        // window.getDecorView().setPadding(0, 0, 0, 0);
        //设置对话框横向铺满
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
        //设置对话框动画
        window.setWindowAnimations(R.style.ShareDialog_Animation);
        //因为某些机型是虚拟按键的,所以要加上以下设置防止挡住按键.
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
}
