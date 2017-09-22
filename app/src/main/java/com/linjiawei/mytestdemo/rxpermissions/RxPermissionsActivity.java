package com.linjiawei.mytestdemo.rxpermissions;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class RxPermissionsActivity extends ToolbarBaseActivity{

    @Bind(R.id.request_camera_permission)
    TextView mRequestCameraPermission;
    @Bind(R.id.request_sd_permission)
    TextView mRequestCameraSd;

    @Override
    protected int getContentView() {
        return R.layout.activity_rx_permissions;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setTitle(getIntent().getExtras().getString(ACTIVITY_TITLE_NAME));
    }


    @OnClick(R.id.request_camera_permission)
    void requestCameraPermission(View view) {
        RxPermissions.getInstance(this).request(Manifest.permission.CAMERA).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    //用户同意
                    ToastUtils.showShort("用户同意申请照相权限");
                } else {
                    //用户拒绝
                    ToastUtils.showShort("用户拒绝申请照相权限");
                }
            }
        });
    }

    @OnClick(R.id.request_sd_permission)
    void requestSDPermission(View view){
        RxPermissions.getInstance(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    ToastUtils.showShort("用户同意申请读取外部SD卡权限");
                } else {
                    ToastUtils.showShort("用户拒绝申请读取外部SD卡权限");
                }

            }
        });
    }


}
