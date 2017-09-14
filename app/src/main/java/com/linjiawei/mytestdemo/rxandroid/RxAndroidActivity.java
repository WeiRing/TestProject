package com.linjiawei.mytestdemo.rxandroid;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.linjiawei.mytestdemo.OnRecycleItemClickListener;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 张鸿洋: Android 万能的Adapter for ListView,RecyclerView,GridView等，支持多种Item类型的情况。
 * 引用方式参照：
 * http://blog.csdn.net/lmj623565791/article/details/51118836
 * https://github.com/hongyangAndroid/baseAdapter
 */

public class RxAndroidActivity extends ToolbarBaseActivity {
    @Bind(R.id.rxAndroid_recycleView)
    RecyclerView mRxAndroidRecycleView;
    private List<String> dataList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_rx_android;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setTitle(getIntent().getExtras().getString(ACTIVITY_TITLE_NAME));
        addItem();
        mRxAndroidRecycleView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRxAndroidRecycleView.setAdapter(new CommonAdapter<String>(this, R.layout.rv_item, dataList) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) { }

            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.tv_item_text, s);
            }
        });
        mRxAndroidRecycleView.addOnItemTouchListener(new OnRecycleItemClickListener(mRxAndroidRecycleView) {
            @Override
            public void OnItemClick(RecyclerView.ViewHolder holder) {
                ToastUtils.showShort(dataList.get(holder.getPosition()));
                openNewActivity(dataList.get(holder.getAdapterPosition()), RxAndroidActivity.this, RxDomeActivity.class);
            }
        });
    }

    private void addItem() {
        dataList.add("网络请求：RxJava + OkHttp");
        dataList.add("网络请求：RxJava + OkHttp + GSON");
        dataList.add("防止View被连续点击");
        dataList.add("CheckBox 状态的实时更新");
        dataList.add("搜索关键字提醒");
        dataList.add("Buffer操作符的使用");
        dataList.add("zip数据合并操作");
        dataList.add("Content数据刷新操作");
        dataList.add("轮询器");
        dataList.add("定时器");
        dataList.add("fragment间数据传递");
        dataList.add("RxBus");
        dataList.add("复用订阅者");
    }


}
