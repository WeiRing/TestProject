package com.linjiawei.mytestdemo.rxpermissions;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


public class RxPermissionsActivity extends ToolbarBaseActivity{

    @Bind(R.id.request_camera_permission)
    TextView mRequestCameraPermission;
    @Bind(R.id.request_sd_permission)
    TextView mRequestCameraSd;
    @Bind(R.id.request_callPhone_permission)
    TextView mRequestCallPhone;

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
        //RxPermission 1.0写法
        /*RxPermissions.getInstance(this).request(Manifest.permission.CAMERA).suscribe(new Action1<Boolean>() {
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
        });*/

        //RxPermission 2.0写法
        new RxPermissions(this).request(Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
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
        //RxPermission 1.0写法
        /*RxPermissions.getInstance(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    ToastUtils.showShort("用户同意申请读取外部SD卡权限");
                } else {
                    ToastUtils.showShort("用户拒绝申请读取外部SD卡权限");
                }
            }
        });*/

        //RxPermission 2.0写法
        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    ToastUtils.showShort("用户同意申请读取外部SD卡权限");
                } else {
                    ToastUtils.showShort("用户拒绝申请读取外部SD卡权限");
                }

            }
        });
    }


    /**
     * 将统一请求多个权限，来观察一个详细的结果:
     * https://github.com/tbruyelle/RxPermissions
     * @param view
     */
    @OnClick(R.id.request_callPhone_permission)
    void requestPhone(View view){
        new RxPermissions(this)
                .requestEach(
                    Manifest.permission.READ_CALENDAR,
                    Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.CALL_PHONE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        //根据实际需求，判断是否对每个权限做独立的判断并操作...

                        if (permission.granted) {
                            // 用户已经同意的权限
                            if (permission.name.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {

                            } else if (permission.name.equals(Manifest.permission.CAMERA)) {
                                ToastUtils.showShort(Manifest.permission.CAMERA);
                            }
                            LogUtils.i("permission",permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            LogUtils.i("permission",permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』,需要用去进入手机设置重新开启...
                            LogUtils.i("permission",permission.name + " is denied.");
                        }
                    }
                });
    }


}
