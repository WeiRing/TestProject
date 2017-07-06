package com.linjiawei.mytestdemo;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by LinJiawei on 2017/7/5. 16:35
 * mail:911926881@qq.com
 */

public class ActivityParameter {
    public Class<? extends AppCompatActivity> activity;
    public int number;
    public int imgid;
    public String name;
    public Object parameter;
    public boolean isValid = true;
    public ActivityParameter(int num, String name, Class<? extends AppCompatActivity> activity){
        this.number = num;
        this.name = name;
        this.activity = activity;
    }


    public Class<? extends AppCompatActivity> getActivity() {
        return activity;
    }

    public void setActivity(Class<? extends AppCompatActivity> activity) {
        this.activity = activity;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
