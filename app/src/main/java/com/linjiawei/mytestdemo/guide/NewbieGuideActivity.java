package com.linjiawei.mytestdemo.guide;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.hubert.library.Controller;
import com.app.hubert.library.HighLight;
import com.app.hubert.library.NewbieGuide;
import com.app.hubert.library.OnGuideChangedListener;
import com.blankj.utilcode.util.ToastUtils;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;


/**
 * 一个小巧好用的Android引导蒙版（浮层）库：NewbieGuide
 * 郭霖主要对NewbieGuide调用方式进行了封装，通过链式调用，一行代码就可以实现引导层的实现。
 * 使用说明地址：https://github.com/huburt-Hu/NewbieGuide
 */

public class NewbieGuideActivity extends ToolbarBaseActivity {
    private TextView  nextBtn;

    @Override
    protected int getContentView() {
        return R.layout.activity_newbie_guide;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setTitle(getIntent().getExtras().getString(ACTIVITY_TITLE_NAME));
        nextBtn = (TextView) findViewById(R.id.nextPageBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("下一页...");
            }
        });
    }

    public void showNewbieGuide(View view) {
        NewbieGuide.with(this)//传入activity
                .setOnGuideChangedListener(new OnGuideChangedListener() {
                    @Override
                    public void onShowed(Controller controller) {
                        ToastUtils.showShort("引导浮层显示");
                    }

                    @Override
                    public void onRemoved(Controller controller) {
                        ToastUtils.showShort("引导浮层消失");
                    }
                })
                .setLabel("guide1")//设置引导层标示，用于区分不同引导层，必传！否则报错
                .addHighLight(nextBtn, HighLight.Type.RECTANGLE)//添加需要高亮的view
                .setLayoutRes(R.layout.hint_guide_layout)//自定义的提示layout，不要添加背景色，引导层背景色通过setBackgroundColor()设置
                .alwaysShow(true)//是否每次都显示，默认是显示第一次...
                .show();//显示引导层

        //自定义写法：
        //        Controller controller = NewbieGuide.with(this)
        //                .setOnGuideChangedListener(new OnGuideChangedListener() {//设置监听
        //                    @Override
        //                    public void onShowed(Controller controller) {
        //                        //引导层显示
        //                    }
        //
        //                    @Override
        //                    public void onRemoved(Controller controller) {
        //                        //引导层消失
        //                    }
        //                })
        //                .setBackgroundColor(Color.BLACK)//设置引导层背景色，建议有透明度，默认背景色为：0xb2000000
        //                .setEveryWhereCancelable(false)//设置点击任何区域消失，默认为true
        //                .setLayoutRes(R.layout.view_guide, R.id.textView)//自定义的提示layout,第二个可变参数为点击隐藏引导层view的id
        //                .alwaysShow(true)//是否每次都显示引导层，默认false
        //                .build();//构建引导层的控制器
        //        controller.resetLabel("guide1");
        //        controller.remove();//移除引导层
        //        controller.show();//显示引导层


    }


}
