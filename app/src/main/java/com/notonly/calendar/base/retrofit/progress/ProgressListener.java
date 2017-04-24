package com.notonly.calendar.base.retrofit.progress;

/**
 * 进度回调接口. eg:上传、下载进度
 *
 * @author a_liYa
 * @date 16/8/6 下午3:25.
 */
public interface ProgressListener {
    /**
     * 进度回调 - 转换进度(0 - 100) : (int) (100 * currentBytes / contentLength)
     *
     * @param bytesRead     已进行bytes
     * @param contentLength 总bytes
     * @param done          进行状态
     */
    void onProgress(long bytesRead, long contentLength, Done done);
}