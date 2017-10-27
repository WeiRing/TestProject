package com.linjiawei.mytestdemo.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.linjiawei.mytestdemo.OnRecycleItemClickListener;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class OpenSourceViewsActivity extends ToolbarBaseActivity {

    @Bind(R.id.openSourceViews_recycleView)
    RecyclerView mOSAndroidRecycleView;
    private List<String> dataList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_open_source_views;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setTitle(getIntent().getExtras().getString(ACTIVITY_TITLE_NAME));
        addItem();
        mOSAndroidRecycleView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mOSAndroidRecycleView.setAdapter(new CommonAdapter<String>(this, R.layout.rv_item, dataList) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_item_text, s);
            }
        });
        mOSAndroidRecycleView.addOnItemTouchListener(new OnRecycleItemClickListener(mOSAndroidRecycleView) {
            @Override
            public void OnItemClick(RecyclerView.ViewHolder holder) {
                Bundle bundle = new Bundle();
                bundle.putString(ACTIVITY_TITLE_NAME,dataList.get(holder.getAdapterPosition()));
                bundle.putInt("showViewsType",holder.getAdapterPosition());
                openNewActivity(bundle, OpenSourceViewsActivity.this, OpenSourceDemoActivity.class);
            }
        });
    }

    private void addItem() {
        dataList.add("带圆角的RadioGroup组件");
        dataList.add("广告栏BGABanner控件");
    }


}
