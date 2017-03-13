package com.linjiawei.msdemo;

import android.app.Application;

import org.xutils.DbManager;
import org.xutils.x;


/**
 * Created by : 林嘉伟  Date：2016/10/24
 */

public class MyApp extends Application{

    private static DbManager.DaoConfig daoConfig;
    public static DbManager.DaoConfig getDaoConfig(){
        return daoConfig;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        initDataDao();
    }


    private void initDataDao(){
        daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbName("student_db").setDbVersion(1).setDbUpgradeListener(new DbManager.DbUpgradeListener() {
            @Override
            public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

            }
        });
    }

}
