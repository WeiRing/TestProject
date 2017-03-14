package com.linjiawei.greendaodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.linjiawei.greendaodemo.entity.User;
import com.linjiawei.greendaodemo.greendao.gen.UserDao;

public class MainActivity extends AppCompatActivity {
    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserDao = GreenDaoManager.getDaoManger().getMDaoSession().getUserDao();

        mUserDao.insertOrReplace(new User(123, "林嘉伟", true, 123456));
        mUserDao.insertOrReplace(new User(321, "林伟佳", true, 654321));

        User user = mUserDao.queryBuilder().where(UserDao.Properties.UserName.eq("林伟佳")).build().unique();
        Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show();
//        List list = mUserDao.queryBuilder().list();
//        for (int i = 0; i < list.size(); i++) {
//            Toast.makeText(this, list.get(i).toString(), Toast.LENGTH_LONG).show();
//        }
    }
}
