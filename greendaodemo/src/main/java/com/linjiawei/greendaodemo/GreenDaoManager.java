package com.linjiawei.greendaodemo;


import com.linjiawei.greendaodemo.greendao.gen.DaoMaster;
import com.linjiawei.greendaodemo.greendao.gen.DaoSession;

/**
 * Created by : 林嘉伟  Date：2017/2/27
 */

public class GreenDaoManager {
    private  static GreenDaoManager mInstance;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private GreenDaoManager() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(MyApplication.getContext(),"users-db",null);
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
