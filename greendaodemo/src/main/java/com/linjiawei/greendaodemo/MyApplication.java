package com.linjiawei.greendaodemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by : 林嘉伟  Date：2017/2/27
 */

public class MyApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        GreenDaoManager.getDaoManger();
    }

    public static Context getContext() {
        return mContext;
    }

}
