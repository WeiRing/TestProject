package com.linjiawei.mytestdemo.arouter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;

/**
 * ARouter是阿里2017年开源的页面路由框架
 * https://github.com/alibaba/ARouter
 *
*/
public class ARouterActivity extends ToolbarBaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_arouter;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setTitle(getIntent().getExtras().getString(ACTIVITY_TITLE_NAME));
    }

    //返回时，无回带数据
    public void aRouterClickOne(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("bundleInt",100);
        bundle.putString("bundleString", "bundle hello");
        bundle.putString("titleName", "no result ARouter");
        ARouter.getInstance().build("/myTestRouter/noResult")
                .withString("stringKey", "stringValue")
                .withBoolean("booleanKey", true)
                .withBundle("bundleKey", bundle)
                .navigation();
    }

    //返回时带数返回上一页面...
    public void aRouterClickTwo(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("bundleInt",200);
        bundle.putString("bundleString", "bundle hello");
        bundle.putString("titleName", "no result ARouter");
        ARouter.getInstance().build("/myTestRouter/noResult")
                .withString("stringKey", "传递过去的数据...")
                .withBoolean("booleanKey", true)
                .withBundle("bundleKey", bundle)
                .navigation(this, 1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode==1002 && data != null) {
            String resultData = data.getStringExtra("resultData");
            ToastUtils.showShort(resultData);
        }
    }
}
