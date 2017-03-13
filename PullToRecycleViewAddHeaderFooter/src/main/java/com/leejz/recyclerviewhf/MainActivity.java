package com.leejz.recyclerviewhf;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinJiawei on 2016/2/24.
 * Blog:http://blog.csdn.net/leejizhou
 * QQ:911926881
 *
 *
 * 用法：
 * 添加链接：compile 'com.github.tosslife:pullloadview:1.0.2'
 *
 * PullToLoadView 可以通过getRecyclerView()方法可得到RecycleView
 *
 * PullToLoadView是个完美的控件...
 *
 * 普通recycleView可以采用此方法添加头部或尾部
 *
 */
public class MainActivity extends AppCompatActivity implements PullCallback {
    private PullToLoadView myPullToRecycleView;
    private RecyclerView mRecyclerView;

    private MyRecycleViewAdapter adapter;
    private List<String> dates;

    GridLayoutManager gridLayoutManager;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dates = new ArrayList<String>();
        adapter = new MyRecycleViewAdapter(this,dates);

        myPullToRecycleView = (PullToLoadView) findViewById(R.id.myPullToRefreshView);
        myPullToRecycleView.isLoadMoreEnabled(true);//开启上拉加载更多的功能
        myPullToRecycleView.setLoadMoreOffset(1);//设置距离底部还有多少条数据时执行加载更多的操作，其实就是执行onLoadMore()方法...
        myPullToRecycleView.setPullCallback(this);//此控件必须执行该回调，不然运行会报错...
        myPullToRecycleView.initLoad();//自动加载一定要设置setPullCallback()后面...不然不会自动加载
        //通过从PullToLoadView中取出recycleView...
        mRecyclerView = myPullToRecycleView.getRecyclerView();
        //List布局
      /*  layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);*/

        //类似墙砖风格Grid布局（水平方向错开不对齐）
        gridLayoutManager=new GridLayoutManager(MainActivity.this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(adapter);


        //此方法用来设置某一条数据在gridView列表中占据列数
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.isHeaderView(position) || adapter.isFooterView(position)) {
                    //返回设定的列数
                    return gridLayoutManager.getSpanCount();
                }
                //计算对应算法...
                else if ((position % 4) == 2 || (position % 4) ==3) {
                    //返回2列宽的条目
                    return 2;
                }else {
                    return 1;
                }
            }
        });
    }



    //以下加载数据相关
    private boolean isLoading = false;
    private boolean isHasLoadedAll = false; //判断是否到了最后一页啦
    private int nextPage;

    private void loadData(final int page) {
        isLoading = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isHasLoadedAll) {
                    Toast.makeText(MainActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                }
                if (page > 10) {
                    isHasLoadedAll = true;
                    return;
                }
                //切记：采用此控件在刷新的时候，一定要回调成功后再将旧的数据清除，不能在请求新数据前（在onRefresh方法中）先清除数据；
                if (page == 1) {
                    dates.clear();
                }

                //"java","python","C++","Php",".NET","js","Ruby","Swift"
                dates.add("java");
                dates.add("python");
                dates.add("C++");
                dates.add("Php");
                dates.add("NET");
                dates.add("js");
                dates.add("Ruby");
                dates.add("Swift");
                dates.add("java");
                dates.add("python");
                dates.add("C++");
                dates.add("Php");
                dates.add("NET");
                dates.add("js");
                dates.add("Ruby");
                dates.add("Swift");
                dates.add("java");
                dates.add("python");
                dates.add("C++");
                dates.add("Php");
                dates.add("NET");
                dates.add("js");
                dates.add("Ruby");
                dates.add("Swift");

                myPullToRecycleView.setComplete();
                isLoading = false;
                nextPage = page + 1;

                adapter.notifyDataSetChanged();
            }
        }, 2000);
    }

//以下是上下拉监听的四个方法
    @Override
    public void onLoadMore() {
        loadData(nextPage);
    }

    @Override
    public void onRefresh() {
        isHasLoadedAll = false;
        loadData(1);
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return isHasLoadedAll;
    }

}
