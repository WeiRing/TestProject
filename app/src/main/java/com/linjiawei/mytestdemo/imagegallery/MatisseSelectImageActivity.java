package com.linjiawei.mytestdemo.imagegallery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;
import com.linjiawei.mytestdemo.tools.Glide4Engine;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Matisse 当前最Fashion的图片选择器 -->
 *  详细使用要仔细阅读文档接入流程 https://github.com/zhihu/Matisse  https://www.jianshu.com/p/5ba40cf378c2
 */
public class MatisseSelectImageActivity extends ToolbarBaseActivity {


    private static final int REQUEST_SELECT_IMAGE_CODE = 1020;
    @Bind(R.id.selectTakePhoto)
    Button selectTakePhotoBtn;
    @Bind(R.id.selectPhoto)
    Button selectPhotoBtn;
    @Bind(R.id.selectedImageText)
    TextView selectedImageText;

    @Override
    protected int getContentView() {
        return R.layout.activity_matisse_select_image;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setTitle(getIntent().getExtras().getString(ACTIVITY_TITLE_NAME));
    }

    @OnClick(R.id.selectTakePhoto)
    public void selectTakePhoto(View view){
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE ,Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    Matisse.from(MatisseSelectImageActivity.this)
                            .choose(MimeType.ofAll(), false)
                            .countable(true) //是否显示选中的后的编号
                            .capture(true)
                            .captureStrategy(new CaptureStrategy(true, "com.linjiawei.mytestdemo.fileprovider"))//AndroidManifest.xml中配置fileprovider,并新建新建file_paths_public.xml文件
                            .maxSelectable(9)//最大的选择数量
                    //        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                            .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                            .thumbnailScale(0.85f)//缩略图片清晰度
                            //加载方式
                            //for Picasso
                            //.imageEngine(new PicassoEngine())
                            // for glide-V3
                            //.imageEngine(new GlideEngine())
                            // for glide-V4
                            .imageEngine(new Glide4Engine())
                            .setOnSelectedListener(new OnSelectedListener() {
                                @Override
                                public void onSelected(
                                        @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                                    // DO SOMETHING IMMEDIATELY HERE
                                    ToastUtils.showShort("已选数量："+pathList.size()+"张");
                                }
                            })
                            .originalEnable(true)//是否允许勾选原图开关
                            .maxOriginalSize(10) //允许最大的图片大小10MB
                            .setOnCheckedListener(new OnCheckedListener() {
                                @Override
                                public void onCheck(boolean isChecked) {
                                    // DO SOMETHING IMMEDIATELY HERE
                                    ToastUtils.showShort("是否选中原图: " + isChecked);
                                }
                            })
                            .forResult(REQUEST_SELECT_IMAGE_CODE);

                } else {
                    FancyToast.makeText(MatisseSelectImageActivity.this,"授权失败", Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                }

            }
        });




    }

    @OnClick(R.id.selectPhoto)
    public void selectPhoto() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    Matisse.from(MatisseSelectImageActivity.this)
                            .choose(MimeType.ofImage())
                            .theme(R.style.Matisse_Dracula)
                            .countable(false)//不显示数字标识
                            // .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                            .maxSelectable(9)
                            .originalEnable(false)//是否允许勾选原图开关
                            .maxOriginalSize(10)
                            .thumbnailScale(0.8f)//缩略图片清晰度
                            .imageEngine(new PicassoEngine())//采用Picasso的方式加载图片
                            .forResult(REQUEST_SELECT_IMAGE_CODE);
                } else {
                    FancyToast.makeText(MatisseSelectImageActivity.this,"授权失败", Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                }

            }
        });
    }

    //选中图片确认后的回调监听
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_IMAGE_CODE && resultCode == RESULT_OK) {
            List<String> list = Matisse.obtainPathResult(data);//取到Path
            List<Uri> uris = Matisse.obtainResult(data);//取到Uri
            if (list.isEmpty()) {
                return;
            }
            StringBuffer tempString = new StringBuffer();
            for (int i = 0; i <list.size(); i++) {
                tempString.append((i+1)+". ").append(list.get(i)).append("\n\n");
            }
            String getDate = tempString.toString();
            selectedImageText.setText(getDate);
        }
    }


}
