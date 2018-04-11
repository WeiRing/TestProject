package com.linjiawei.mytestdemo;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
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
        initARouter();
    }

    public static Context getContext() {
        return mContext;
    }

    //初始化标记2 初始化万能工具类
    private void initUtilCode(){
        Utils.init(this);
    }

    /**
     * 初始化Router相关配置...
     */
    private void initARouter(){
        if (AppUtils.isAppDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

}
