package com.linjiawei.mytestdemo;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.androidnetworking.AndroidNetworking;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.Utils;
import com.bolex.autoEx.AutoEx;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * Created by : 林嘉伟  Date：2017/7/5
 */

public class MyApplication extends Application {
    private static Context mContext;


    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initUtilCode();
        initARouter();
        initSwipeBackHelper();
        initFastNetworking();
        initAutoException();
        initGoogleAnalytics();
    }

    public static Context getContext() {
        return mContext;
    }

    //初始化标记2 初始化万能工具类
    private void initUtilCode() {
        Utils.init(this);
    }

    /**
     * 初始化Router相关配置...
     */
    private void initARouter() {
        if (AppUtils.isAppDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早初始化，推荐在Application中初始化
    }

    /**
     * 初始化手势划动框架
     */
    private void initSwipeBackHelper() {
        BGASwipeBackHelper.init(this, null);
    }

    private void initFastNetworking() {
        AndroidNetworking.initialize(mContext);
    }

    /**
     * 出现异常闪退时，会自动记录异常信息，并指引解决办法
     */
    private void initAutoException(){
        AutoEx.apply(mContext);
    }

    private void initGoogleAnalytics(){
        sAnalytics = GoogleAnalytics.getInstance(this);
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }
        return sTracker;
    }

}
