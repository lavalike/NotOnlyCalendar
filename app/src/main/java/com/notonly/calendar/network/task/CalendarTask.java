package com.notonly.calendar.network.task;

import com.dimeno.network.callback.RequestCallback;
import com.dimeno.network.task.GetTask;

/**
 * CalendarTask
 * Created by wangzhen on 2020/6/13.
 */
public class CalendarTask extends GetTask {
    private String date;

    public <EntityType> CalendarTask(RequestCallback<EntityType> callback) {
        super(callback);
    }

    @Override
    public void onSetupParams(Object... params) {

    }

    public CalendarTask setDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public String getApi() {
        return "/holiday/single/" + date;
    }
}
