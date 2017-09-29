package com.linjiawei.mytestdemo.view;

import android.os.Bundle;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;
import com.linjiawei.mytestdemo.interfacebase.OnFragmentInteractionListener;
import com.linjiawei.mytestdemo.view.fragments.SegmentedGroupFragment;


public class OpenSourceDemoActivity extends ToolbarBaseActivity implements OnFragmentInteractionListener {

    private int showType;

    @Override
    protected int getContentView() {
        return R.layout.activity_open_source_demo;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setTitle(getIntent().getExtras().getString(ACTIVITY_TITLE_NAME));
        showType = getIntent().getExtras().getInt("showViewsType");
        addFragmentType(showType);
    }

    private void addFragmentType(int type){
        switch (type) {
            case 0:
                FragmentUtils.addFragment(getSupportFragmentManager(), SegmentedGroupFragment.newInstance("", ""), R.id.osViewsFragmentContentLayout);
                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            default:
                ToastUtils.showShort("无数据...");
                break;
        }
    }


    @Override
    public void onFragmentInteraction(int type) {

    }
}
