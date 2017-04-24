package com.notonly.calendar.base.retrofit.progress;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 包装的RequestBody(请求体)，处理进度
 * <p>
 * 装饰设计模式
 *
 * @author a_liYa
 * @date 16/8/6 下午3:57.
 */
public class ProgressRequestBody extends RequestBody {
    //实际的待包装请求体
    private final RequestBody requestBody;
    //进度回调接口
    private final ProgressListener progressListener;
    //包装完成的BufferedSink
    private BufferedSink bufferedSink;
    // 进行的状态
    private Done done;

    /**
     * 构造函数，赋值
     *
     * @param requestBody      待包装的请求体
     * @param progressListener 回调接口
     */
    public ProgressRequestBody(RequestBody requestBody, ProgressListener progressListener) {
        this.requestBody = requestBody; // 注入
        this.progressListener = progressListener;
    }

    /**
     * 重写调用实际的响应体的contentType
     *
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    /**
     * 重写调用实际的响应体的contentLength
     *
     * @return contentLength
     * @throws IOException 异常
     */
    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    /**
     * 重写进行写入
     *
     * @param sink BufferedSink
     * @throws IOException 异常
     */
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        requestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();

    }

    /**
     * 写入，回调进度接口
     *
     * @param sink Sink
     * @return Sink
     */
    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount); // Tag_Exception : IllegalStateException: closed
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                //回调
                if (progressListener != null) {
                    if (done == null) {
                        done = Done.START_UP;
                        progressListener.onProgress(0, contentLength, done);
                    }

                    if (bytesWritten == contentLength) {
                        progressListener.onProgress(bytesWritten, contentLength, done);
                        done = Done.FINISH_UP;
                    } else {
                        done = Done.PROCESS_UP;
                    }
                    progressListener.onProgress(bytesWritten, contentLength, done);
                }
            }
        };
    }
}