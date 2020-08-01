package com.notonly.calendar.network.task;

import com.dimeno.network.callback.RequestCallback;
import com.dimeno.network.task.GetTask;
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
