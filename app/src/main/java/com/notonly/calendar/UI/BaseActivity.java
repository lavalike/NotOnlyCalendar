package com.notonly.calendar.UI;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhen on 16/1/12.
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * 统一管理网络任务
     */
    private List<Callback.Cancelable> mTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setElevation(0);
        mTasks = new ArrayList<>();
    }

    /**
     * 将任务添加到集合中
     *
     * @param task
     */
    public void addTaskToList(Callback.Cancelable task) {
        if (mTasks != null) {
            if (!mTasks.contains(task)) {
                mTasks.add(task);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTasks != null) {
            //停止所有网络任务
            for (Callback.Cancelable task : mTasks) {
                if (!task.isCancelled()) {
                    task.cancel();
                }
            }
            mTasks.clear();
        }
    }
}
