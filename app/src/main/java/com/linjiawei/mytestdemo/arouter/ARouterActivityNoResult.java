package com.linjiawei.mytestdemo.arouter;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;

@Route(path = "/myTestRouter/noResult")
public class ARouterActivityNoResult extends ToolbarBaseActivity {
    @Autowired
    public String stringKey;
    @Autowired
    public boolean booleanKey;
    @Autowired
    public Bundle bundleKey;

    @Override
    protected int getContentView() {
        return R.layout.activity_arouter_no_result;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);//自动注入加载对应的数据
        if (bundleKey != null) {
            setTitle(bundleKey.getString("titleName"));
            setToolbarLeftBthListener(R.mipmap.ic_reply_white, new OnClickToolbarBtnListener() {
                @Override
                public void click() {
                    onBackPressed();
                }
            });
        }
        ToastUtils.showShort(stringKey);
    }


    @Override
    public void onBackPressed() {
        if (stringKey.equals("传递过去的数据...")){
            Intent intent = new Intent();
            intent.putExtra("resultData", "返回的数据：" + stringKey);
            this.setResult(1002, intent);
        }
        super.onBackPressed();//切记...
    }

}
