package com.notonly.calendar.network.task;

import com.dimeno.network.callback.RequestCallback;
import com.dimeno.network.task.GetTask;

/**
 * HistoryTask
 * Created by wangzhen on 2020/6/13.
 */
public class HistoryTask extends GetTask {
    public static final int TYPE_NO_DETAILS = 0;
    public static final int TYPE_NEED_DETAILS = 1;

    public <EntityType> HistoryTask(RequestCallback<EntityType> callback) {
        super(callback);
    }

    @Override
    public void onSetupParams(Object... params) {
        put("type", params[0]);//type：是否需要详情，0：不需要详情 1：需要详情 默认值 0 可不传
    }

    @Override
    public String getApi() {
        return "/history/today";
    }
}
