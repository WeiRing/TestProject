package com.linjiawei.mytestdemo.rxandroid;

import android.os.Bundle;

import com.linjiawei.mytestdemo.MainActivity;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;

public class RxAndroidActivity extends ToolbarBaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_rx_android;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String titleName = getIntent().getExtras().getString(MainActivity.ACTIVITY_TITLE_NAME);
        setTitle(titleName);
    }
}
