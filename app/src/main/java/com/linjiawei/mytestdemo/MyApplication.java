package com.linjiawei.mytestdemo;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;

/**
 * Created by : 林嘉伟  Date：2017/7/5
 */

public class MyApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initUtilCode();
    }

    public static Context getContext() {
        return mContext;
    }

    //初始化标记2 初始化万能工具类
    private void initUtilCode(){
        Utils.init(mContext);
    }

}
