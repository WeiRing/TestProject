package com.linjiawei.revisedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PullCallback {

    private RecyclerView myRecycleView;
    private PullToLoadView myPullToLoadView;
    private List<String> dates;
    private MyRecycleViewAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        myPullToLoadView = (PullToLoadView) findViewById(R.id.myPullToRefreshView);
//        myPullToLoadView.isLoadMoreEnabled(true);//开启上拉加载更多的功能
//        myPullToLoadView.setLoadMoreOffset(1);//设置距离底部还有多少条数据时执行加载更多的操作，其实就是执行onLoadMore()方法...
//        myPullToLoadView.setPullCallback(this);//此控件必须执行该回调，不然运行会报错...
//        myPullToLoadView.initLoad();//自动加载一定要设置setPullCallback()后面...不然不会自动加载
//        myRecycleView = myPullToLoadView.getRecyclerView();
        myRecycleView = (RecyclerView) findViewById(R.id.myRecycle);
        dates = new ArrayList<>();
        initData();
        myAdapter = new MyRecycleViewAdapter(this, dates);
        myRecycleView.setLayoutManager(new GridLayoutManager(this,2));
        myRecycleView.setAdapter(myAdapter);
        myRecycleView.addOnItemTouchListener(new OnRecycleItemClickListener(myRecycleView) {
            @Override
            public void OnItemClick(RecyclerView.ViewHolder holder) {

            }
            //需要长按事件时，重新父类销父类的onLongItemClick方法
            @Override
            public void onLongItemClick(RecyclerView.ViewHolder holder) {
                super.onLongItemClick(holder);//长按事件
            }
        });

    }


    private void initData(){
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

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public boolean isLoading() {
        return false;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return false;
    }
}
