package com.linjiawei.mytestdemo.greendao;


import com.linjiawei.mytestdemo.MyApplication;
import com.linjiawei.mytestdemo.greendao.dao.DaoMaster;
import com.linjiawei.mytestdemo.greendao.dao.DaoSession;

/**
 * Created by : 林嘉伟  Date：2017/7/7
 */

public class GreenDaoManager {
    private  static GreenDaoManager mInstance;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private GreenDaoManager() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(MyApplication.getContext(),"person-db",null);
        mDaoMaster = new DaoMaster(openHelper.getWritableDb());
        mDaoSession = mDaoMaster.newSession();
    }

    public static GreenDaoManager getDaoManger (){
        if (mInstance == null) {
            mInstance = new GreenDaoManager();
        }
        return mInstance;
    }

    public DaoMaster getMDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getMDaoSession() {
        return mDaoSession;
    }
}
