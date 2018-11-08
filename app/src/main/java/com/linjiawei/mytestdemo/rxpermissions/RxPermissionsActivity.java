package com.linjiawei.mytestdemo.rxpermissions;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

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

    @OnClick(R.id.request_AllDanger_permission)
    public void requestAllDangerPermission(View view){
        XXPermissions.with(this)
                .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                /*.permission(com.hjq.permissions.Permission.REQUEST_INSTALL_PACKAGES) //支持8.0及以上请求安装权限
                .permission(com.hjq.permissions.Permission.SYSTEM_ALERT_WINDOW) //支持请求6.0及以上悬浮窗权限
                .permission(com.hjq.permissions.Permission.Group.STORAGE) //不指定权限则自动获取清单中的危险权限*/
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        ToastUtils.showShort("isAll : " + isAll);
                        LogUtils.i("permissionTag:", granted.toString());
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        LogUtils.i("noPermissionTag:", denied.toString());
                    }
                });
    }

    @OnClick(R.id.request_unknownInstall_permission)
    public void requestUnknownInstallPermission(View view) {
        XXPermissions.with(this)
                .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                .permission(com.hjq.permissions.Permission.REQUEST_INSTALL_PACKAGES) //支持8.0及以上请求安装权限
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        ToastUtils.showShort("isAll : " + isAll);
                        LogUtils.i("permissionINSTALL_PACKAGES:", granted.toString());
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        LogUtils.i("noPermissionINSTALL_PACKAGES:", denied.toString());
                    }
                });
    }

    @OnClick(R.id.readSDCardPath)
    public void readSDCardPath(View view){
        String sdPath = getSDPath();
        ToastUtils.showLong(sdPath);
        LogUtils.i(sdPath);
        File file = new File(getSDPath()+"/"+"installTest.apk");
        ToastUtils.showLong(file.getName());
    }


    public static String getSDPath() {
        File sdDir = null;
        // 判断sd卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
            return sdDir.toString();
        } else {
            return Environment.getExternalStorageDirectory().getPath();
        }
    }

    /**
     * 详细参考Android7.0更新安装方法：https://blog.csdn.net/chxc_yy/article/details/81536875?utm_source=blogxgwz1
     * @param view
     */
    @OnClick(R.id.installAPK)
    public void installAPK(View view){
        //7.0以下系统的升级安装方式
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        // 由于没有在Activity环境下启动Activity,设置下面的标签
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setDataAndType(Uri.fromFile(new File(getSDPath()+"/"+"installTest.apk")),
//        "application/vnd.android.package-archive");
//        this.startActivity(intent);

        //7.0及以上系统的升级安装方式
        Intent intent = new Intent(Intent.ACTION_VIEW);
    //    File file = new File(getSDPath()+"/installFile/"+"installTest.apk");
        File file = new File(getSDPath()+"/"+"installFail2.apk");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) {//大于7.0使用此方法
            Uri apkUri =FileProvider.getUriForFile(this, "com.linjiawei.mytestdemo.fileprovider", file);///-----ide文件提供者名
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else {//小于7.0就简单了
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        }
        startActivity(intent);



    }



}
