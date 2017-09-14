package com.linjiawei.mytestdemo.rxandroid;

import android.os.Bundle;

import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;

public class RxDomeActivity extends ToolbarBaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_rx_dome;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setTitle(getIntent().getExtras().getString(ACTIVITY_TITLE_NAME));
    }
}
