package com.notonly.calendar.base.retrofit.progress;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 包装的ResponseBody(响应体)，处理进度
 * <p>
 * 装饰设计模式
 *
 * @author a_liYa
 * @date 16/8/6 下午3:30.
 */
public class ProgressResponseBody extends ResponseBody {
    //实际的待包装响应体
    private final ResponseBody responseBody;
    //进度回调接口
    private final ProgressListener progressListener;
    //包装完成的BufferedSource
    private BufferedSource bufferedSource;
    // 进行的状态
    private Done done;

    /**
     * 构造函数，赋值
     *
     * @param responseBody     待包装的响应体
     * @param progressListener 回调接口
     */
    public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
        this.responseBody = responseBody; // 注入
        this.progressListener = progressListener;
    }


    /**
     * 重写调用实际的响应体的contentType
     *
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    /**
     * 重写调用实际的响应体的contentLength
     *
     * @return contentLength
     * @throws IOException 异常
     */
    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    /**
     * 重写进行包装source
     *
     * @return BufferedSource
     * @throws IOException 异常
     */
    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            //包装
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    /**
     * 读取，回调进度接口
     *
     * @param source Source
     * @return Source
     */
    private Source source(Source source) {

        return new ForwardingSource(source) {
            //当前读取字节数
            long totalBytesRead = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);

                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }

                //增加当前读取的字节数，如果读取完成了bytesRead会返回-1
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                //回调，如果contentLength不知道长度，会返回-1
                if (progressListener != null) {
                    if (done == null) {
                        done = Done.START_DOWN;
                        progressListener.onProgress(0, contentLength, done); // 开始回调
                    }

                    if (bytesRead == -1) {
                        if (done == Done.FINISH_DOWN) {
                            return bytesRead;
                        }
                        done = Done.FINISH_DOWN;
                    } else {
                        done = Done.PROCESS_DOWN;
                    }
                    progressListener.onProgress(totalBytesRead, contentLength, done);
                }
                return bytesRead;
            }
        };
    }
}