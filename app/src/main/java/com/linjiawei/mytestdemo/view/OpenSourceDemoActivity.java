package com.linjiawei.mytestdemo.view;

import android.os.Bundle;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;
import com.linjiawei.mytestdemo.interfacebase.OnFragmentInteractionListener;
import com.linjiawei.mytestdemo.view.fragments.BGABannerFragment;
import com.linjiawei.mytestdemo.view.fragments.GlideLoaderUtilFragment;
import com.linjiawei.mytestdemo.view.fragments.RxImagePickerFragment;
import com.linjiawei.mytestdemo.view.fragments.SegmentedGroupFragment;
import com.linjiawei.mytestdemo.view.fragments.StatusBarUtilFragment;


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
                FragmentUtils.addFragment(getSupportFragmentManager(), BGABannerFragment.newInstance("", ""), R.id.osViewsFragmentContentLayout);
                break;
            case 2:
                FragmentUtils.addFragment(getSupportFragmentManager(), StatusBarUtilFragment.newInstance("", ""), R.id.osViewsFragmentContentLayout);
                break;
            case 3:
                FragmentUtils.addFragment(getSupportFragmentManager(), GlideLoaderUtilFragment.newInstance("", ""), R.id.osViewsFragmentContentLayout);
                break;
            case 4:
                FragmentUtils.addFragment(getSupportFragmentManager(), RxImagePickerFragment.newInstance("", ""), R.id.osViewsFragmentContentLayout);
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
