package com.linjiawei.mytestdemo.imagegallery;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 强大的图库相册选择、拍照
 * <p>
 * GalleryFinal简介：https://github.com/pengjianbo/GalleryFinal
 * Android自定义相册，实现了拍照、图片选择（单选/多选）、 裁剪（单/多裁剪）、旋转、ImageLoader无绑定任由开发者选择、功能可配置、主题样式可配置。GalleryFinal为你定制相册。
 * <p>
 * 为什么要使用GalleryFinal？
 * 拍照/选择图片倒立问题
 * 市场上各种相机和图片浏览器泛滥导致各种异常问题
 * 各种手机兼容性问题引起crash
 * 系统Gallery App不能进行多选
 * 系统拍照/选择图片/裁剪视乎不太好用
 * 系统Gallery App不美观
 */

public class GalleryFinalActivity extends ToolbarBaseActivity {

    @Bind(R.id.radioSelectPhoto)
    Button mRadioSelectPhoto;
    @Bind(R.id.multipleSelectPhoto)
    Button mMultipleSelectPhoto;
    @Bind(R.id.showImageText)
    TextView mShowImageText;

    @Override
    protected int getContentView() {
        return R.layout.activity_gallery_final;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setTitle(getIntent().getExtras().getString(ACTIVITY_TITLE_NAME));
    }

    /**
     * 打开自定义风格的相册
     */
    @OnClick(R.id.radioSelectPhoto)
    public void openRadioResult(View view) {
        //自定义单选
        /*RxGalleryFinal
                .with(this)
                .image()
                .radio()
                .cropAspectRatioOptions(0, new AspectRatio("3:3", 30, 10))
                .crop()
                .imageLoader(ImageLoaderType.PICASSO)
                .subscribe(new RxBusResultDisposable<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                        Toast.makeText(getBaseContext(), "选中了图片路径：" + imageRadioResultEvent.getResult().getOriginalPath(), Toast.LENGTH_SHORT).show();
                    }
                })
                .openGallery();*/
    }
}
