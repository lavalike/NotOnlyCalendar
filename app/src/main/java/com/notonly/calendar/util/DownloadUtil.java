package com.notonly.calendar.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.notonly.calendar.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 文件下载工具类
 * Created by wangzhen on 2017/6/26.
 */
public class DownloadUtil {
    volatile private static DownloadUtil mInstance;
    private static OnDownloadListener mListener;
    private static String fileName;
    private static String dir;
    private static final int MSG_SUCCESS = 0x1;
    private static final int MSG_FAIL = 0x2;
    private static final int MSG_LOADING = 0x3;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SUCCESS:
                    String path = msg.obj != null ? String.valueOf(msg.obj) : "";
                    if (mListener != null) {
                        mListener.onSuccess(path);
                    }
                    break;
                case MSG_FAIL:
                    String err = msg.obj != null ? String.valueOf(msg.obj) : "";
                    if (mListener != null) {
                        mListener.onFail(err);
                    }
                    break;
                case MSG_LOADING:
                    int progress = msg.obj != null ? (int) msg.obj : 0;
                    if (mListener != null) {
                        mListener.onLoading(progress);
                    }
                    break;
            }
        }
    };

    public static DownloadUtil get() {
        if (mInstance == null) {
            synchronized (DownloadUtil.class) {
                if (mInstance == null)
                    mInstance = new DownloadUtil();
            }
        }
        fileName = "";
        dir = "";
        mListener = null;//将listener置null，如未设置listener则会受上一次下载所设置listener的影响
        return mInstance;
    }

    public DownloadUtil() {
    }

    public DownloadUtil setDir(String dir) {
        DownloadUtil.dir = dir;
        return this;
    }

    public DownloadUtil setFileName(String fileName) {
        DownloadUtil.fileName = fileName;
        return this;
    }

    public DownloadUtil setListener(OnDownloadListener listener) {
        mListener = listener;
        return this;
    }

    public void download(String url) {
        download(url, dir, fileName);
    }

    public void download(String url, String dir, String fileName) {
        if (TextUtils.isEmpty(url)) {
            onFail(UIUtils.getString(R.string.error_invalid_apk_url));
            return;
        }
        if (TextUtils.isEmpty(fileName))
            fileName = getNameFromUrl(url);
        if (TextUtils.isEmpty(fileName)) {
            onFail(UIUtils.getString(R.string.error_invalid_apk_url));
            return;
        }
        if (TextUtils.isEmpty(dir))
            dir = PathUtil.getDownloadPath();
        if (url.startsWith("/")) {
            File file = new File(url);
            dir = PathUtil.getDownloadPath();
            if (file.exists()) {
                copy2Folder(url, dir);
            } else {
                onFail(UIUtils.getString(R.string.error_invalid_apk_url));
            }
        } else if (url.startsWith("http") || url.startsWith("https")) {
            innerDownload(url, dir, fileName);
        }
    }

    /**
     * 将文件复制到指定目录
     *
     * @param url 文件路径
     * @param dir 指定目录
     */
    public void copy2Folder(String url, String dir) {
        String destinationPath = dir + File.separator + getNameFromUrl(url);
        try {
            FileInputStream fis = new FileInputStream(url);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
            FileOutputStream fos = new FileOutputStream(destinationPath);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fos);
            int len;
            while ((len = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(len);
            }
            bufferedInputStream.close();
            bufferedOutputStream.close();
            onSuccess(destinationPath);
        } catch (Exception e) {
            onFail(e.getMessage());
        }
    }

    /**
     * 下载网络图片
     *
     * @param url 图片地址
     */
    private void innerDownload(final String url, final String dir, final String fileName) {
        Request request = new Request.Builder().url(url).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String error;
                if (e instanceof SocketTimeoutException) {
                    error = "连接超时";
                } else {
                    error = e.getMessage();
                }
                onFail(error);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                FileOutputStream fos = null;
                byte[] buff = new byte[2048];
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(dir, fileName);
                    if (!file.exists())
                        file.createNewFile();
                    fos = new FileOutputStream(file);
                    int len;
                    long sum = 0;
                    while ((len = is.read(buff)) != -1) {
                        fos.write(buff, 0, len);
                        sum += len;
                        int progress = (int) ((sum * 1.0f) / total * 100);
                        onLoading(progress);
                    }
                    fos.flush();
                    onSuccess(dir + File.separator + fileName);
                } catch (Exception e) {
                    onFail(e.getMessage());
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void onSuccess(String url) {
        Message message = Message.obtain();
        message.what = MSG_SUCCESS;
        message.obj = url;
        handler.sendMessage(message);
    }

    private void onLoading(int progress) {
        Message message = Message.obtain();
        message.what = MSG_LOADING;
        message.obj = progress;
        handler.sendMessage(message);
    }

    private void onFail(String err) {
        Message message = Message.obtain();
        message.what = MSG_FAIL;
        message.obj = err;
        handler.sendMessage(message);
    }

    /**
     * 从下载连接中解析出文件名,过滤掉webp带?的格式
     *
     * @param url url
     * @return 文件名
     */
    private String getNameFromUrl(String url) {
        String result = url.substring(url.lastIndexOf("/") + 1);
        if (result.contains("?")) {
            result = result.substring(0, result.lastIndexOf("?"));
        }
        return result;
    }

    public interface OnDownloadListener {
        /**
         * 下载进度
         *
         * @param progress 进度
         */
        void onLoading(int progress);

        /**
         * 下载成功
         *
         * @param path 文件路径
         */
        void onSuccess(String path);

        /**
         * 下载失败
         *
         * @param err 错误信息
         */
        void onFail(String err);
    }

    public static class H24DownloadCallback implements OnDownloadListener {

        @Override
        public void onLoading(int progress) {

        }

        @Override
        public void onSuccess(String path) {

        }

        @Override
        public void onFail(String err) {

        }
    }
}
