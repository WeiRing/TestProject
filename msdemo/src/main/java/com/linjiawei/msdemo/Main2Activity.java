package com.linjiawei.msdemo;

import android.os.Bundle;

import com.zhy.autolayout.AutoLayoutActivity;

public class Main2Activity extends AutoLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getFragmentManager().beginTransaction().add(R.id.activity_main2,RecyFragment.newInstance(),"").commitAllowingStateLoss();

    }
}
