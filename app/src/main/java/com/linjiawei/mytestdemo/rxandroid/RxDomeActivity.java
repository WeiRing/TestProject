package com.linjiawei.mytestdemo.rxandroid;

import android.os.Bundle;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;
import com.linjiawei.mytestdemo.rxandroid.fragment.RxNetwrokRequest;
import com.linjiawei.mytestdemo.rxandroid.fragment.RxLoopFragment;
import com.linjiawei.mytestdemo.rxandroid.fragment.RxTimerFragment;

public class RxDomeActivity extends ToolbarBaseActivity implements OnFragmentInteractionListener {
    private int showType = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_rx_dome;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setTitle(getIntent().getExtras().getString(ACTIVITY_TITLE_NAME));
        showType = getIntent().getExtras().getInt("showRxType");
        addFragmentType(showType);//加载对应的Fragment模块

        ToastUtils.showShort(showType + ":" + getIntent().getExtras().getString(ACTIVITY_TITLE_NAME));
    }

    private void addFragmentType(int type){
        switch (type) {
            case 0:
                FragmentUtils.addFragment(getSupportFragmentManager(), RxNetwrokRequest.newInstance("", ""), R.id.rxFragmentContent);
                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;
            case 8:
                FragmentUtils.addFragment(getSupportFragmentManager(), RxLoopFragment.newInstance("", ""), R.id.rxFragmentContent);
                break;
            case 9:
                FragmentUtils.addFragment(getSupportFragmentManager(), RxTimerFragment.newInstance("", ""), R.id.rxFragmentContent);
                break;
            case 10:

                break;
            case 11:

                break;
            case 12:

                break;
        }
    }





    @Override
    public void onFragmentInteraction(int type) {

    }



}
