package com.notonly.calendar.network.task;

import com.wangzhen.network.callback.RequestCallback;
import com.wangzhen.network.task.GetTask;
import com.notonly.calendar.base.manager.APIManager;

/**
 * SloganTask
 * Created by wangzhen on 2020/6/13.
 */
public class SloganTask extends GetTask {
    public <EntityType> SloganTask(RequestCallback<EntityType> callback) {
        super(callback);
    }

    @Override
    public void onSetupParams(Object... params) {

    }

    @Override
    public String getApi() {
        return APIManager.URL_SLOGAN;
    }
}
