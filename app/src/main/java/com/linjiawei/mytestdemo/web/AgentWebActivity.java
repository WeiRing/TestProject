package com.linjiawei.mytestdemo.web;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;


/***
 * AgentWeb 是基于 Android WebView 一个轻量级浏览器库 ， 使用很方便 ， 集成进你应用 ，只需几行代码 。
 * 接入介绍：https://github.com/Justson/AgentWeb
 *
 * 这个框架也不错，可以看看：
 * http://blog.csdn.net/jdsjlzx/article/details/51282689
 */

public class AgentWebActivity extends ToolbarBaseActivity {
    AgentWeb mAgentWeb;
    LinearLayout mLinearLayout;

    @Override
    protected int getContentView() {
        return R.layout.activity_agent_web;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setTitle("林嘉伟-专属VIP通道");
        mLinearLayout = (LinearLayout) findViewById(R.id.agentWebParentLayout);
        mAgentWeb = AgentWeb.with(this)//传入Activity or Fragment
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                .useDefaultIndicator()// 使用默认进度条
                .setIndicatorColor(getResources().getColor(R.color.colorAccent))
            //    .defaultProgressBarColor() // 使用默认进度条颜色
                .setReceivedTitleCallback(new ChromeClientCallbackManager.ReceivedTitleCallback() {
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        ToastUtils.showShort(title);
                    }
                }) //设置 Web 页面的 title 回调
                .createAgentWeb()//
                .ready()
                .go("http://www.jd.com");
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }



    //200ms内点击两次才调用返回
    long mExitTime = 0;
    @Override
    public void onBackPressed() {
        //1.点击的时间差如果大于2000，则提示用户点击两次退出
        if(System.currentTimeMillis() - mExitTime > 2000) {
            //2.保存当前时间
            mExitTime  = System.currentTimeMillis();
            //3.提示
            ToastUtils.showShort("再点击一次退出京东...");
        } else {
            //4.点击的时间差小于2000，调用父类onBackPressed方法执行退出。
            super.onBackPressed();
        }
    }
}
