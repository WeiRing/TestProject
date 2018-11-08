package com.linjiawei.mytestdemo.googleanalytics;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.linjiawei.mytestdemo.MyApplication;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;

import butterknife.ButterKnife;

public class GoogleAnalyticsActivity extends ToolbarBaseActivity {

    Tracker mTracker;

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication application = (MyApplication) getApplication();
        mTracker = application.getDefaultTracker();
        LogUtils.i("GoogleAnalytics page " + "GoogleAnalyticsActivity");
        mTracker.setScreenName("GoogleAnalytics page " + "GoogleAnalyticsActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_google_analytics;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setTitle(getIntent().getExtras().getString(ACTIVITY_TITLE_NAME));
    }


    public void clickEvent1(View view) {
        mTracker.setScreenName("ScreenName-屏幕1:GoogleAnalyticsActivity1");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action1")
                .setAction("Share 点击事件-1")
                .build());
        ToastUtils.showShort("点击事件-1");

    }


    public void clickEvent2(View view) {
        mTracker.setScreenName("ScreenName-屏幕2:GoogleAnalyticsActivity2");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action2")
                .setAction("Share 点击事件-2")
                .build());
        ToastUtils.showShort("点击事件-2");
    }


    public void clickEvent3(View view) {
        mTracker.setScreenName("ScreenName-屏幕3:GoogleAnalyticsActivity3");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action3")
                .setAction("Share 点击事件-3")
                .build());
        ToastUtils.showShort("点击事件-3");
    }


    public void clickEvent4(View view) {
        mTracker.setScreenName("ScreenName-屏幕4:GoogleAnalyticsActivity4");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action4")
                .setAction("Share 点击事件-4")
                .build());
        ToastUtils.showShort("点击事件-4");
    }


    public void clickEvent5(View view) {
        mTracker.setScreenName("ScreenName-屏幕5:GoogleAnalyticsActivity5");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action5")
                .setAction("Share 点击事件-5")
                .build());
        ToastUtils.showShort("点击事件-5");

    }


    public void clickEvent6(View view) {
        mTracker.setScreenName("ScreenName-屏幕6:GoogleAnalyticsActivity6");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action6")
                .setAction("Share 点击事件-6")
                .build());
        ToastUtils.showShort("点击事件-6");
    }


    public void clickEvent7(View view) {
        mTracker.setScreenName("ScreenName-屏幕7:GoogleAnalyticsActivity7");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action7")
                .setAction("Share 点击事件-7")
                .build());
        ToastUtils.showShort("点击事件-7");
    }


    public void clickEvent8(View view) {
        mTracker.setScreenName("ScreenName-屏幕8:GoogleAnalyticsActivity8");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action8")
                .setAction("Share 点击事件-8")
                .build());
        ToastUtils.showShort("点击事件-8");

    }

    public void singleClickEvent(View view) {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("LeftMenu")
                .setAction("openClick")
                .setLabel("my profile page")
                .setValue(30003)
                .build());
        ToastUtils.showShort("独立事件1");

    }

    public void singleClickEvent2(View view) {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("LeftMenu")
                .setAction("openClick")
                .setLabel("my account page")
                .setValue(40004)
                .build());
        ToastUtils.showShort("独立事件2");
    }

}
