package com.linjiawei.mytestdemo.imagegallery;

import android.os.Bundle;

import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;


/**
 * 强大的图库相册选择、拍照
 *
 * GalleryFinal简介：https://github.com/pengjianbo/GalleryFinal
    Android自定义相册，实现了拍照、图片选择（单选/多选）、 裁剪（单/多裁剪）、旋转、ImageLoader无绑定任由开发者选择、功能可配置、主题样式可配置。GalleryFinal为你定制相册。

 为什么要使用GalleryFinal？
 拍照/选择图片倒立问题
 市场上各种相机和图片浏览器泛滥导致各种异常问题
 各种手机兼容性问题引起crash
 系统Gallery App不能进行多选
 系统拍照/选择图片/裁剪视乎不太好用
 系统Gallery App不美观
 */

public class GalleryFinalActivity extends ToolbarBaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_gallery_final;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setTitle(getIntent().getExtras().getString(ACTIVITY_TITLE_NAME));
    }
}
