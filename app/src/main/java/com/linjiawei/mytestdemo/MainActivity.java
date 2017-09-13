package com.linjiawei.mytestdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;
import com.linjiawei.mytestdemo.greendao.GreenDaoActivity;
import com.linjiawei.mytestdemo.guide.NewbieGuideActivity;
import com.linjiawei.mytestdemo.nicedialog.NiceDialogActivity;
import com.linjiawei.mytestdemo.rxandroid.RxAndroidActivity;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ToolbarBaseActivity implements PullCallback {
    public static String ACTIVITY_TITLE_NAME="activityTitleName";
    private PullToLoadView mPullToLoadView;
    private RecyclerView mRecyclerView;
    private HomeRecycleViewAdapter mHomeRecycleViewAdapter;
    private GridLayoutManager mGridLayoutManager;
    private List<ActivityParameter> activityList = new ArrayList<>();


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setTitle("Home list");
        setToolbarRightBthListener(R.mipmap.ic_more_horiz_white, new OnClickToolbarBtnListener() {
            @Override
            public void click() {
                ToastUtils.showShort("click right button listener");
            }
        });
        initMyViews();
    }

    //重写此方法，可以将默认显示的左上角的返回按钮隐藏掉
    @Override
    public boolean isHideBackIcon() {
        return true;
    }

    /**
     * 初始化控件
     */
    private void initMyViews() {
        mPullToLoadView = (PullToLoadView) findViewById(R.id.homePullToRefreshView);
        mPullToLoadView.isLoadMoreEnabled(true);//开启上拉加载更多的功能
        mPullToLoadView.setLoadMoreOffset(1);//设置距离底部还有多少条数据时执行加载更多的操作，其实就是执行onLoadMore()方法...
        mPullToLoadView.setPullCallback(this);//此控件必须执行该回调，不然运行会报错...
        mPullToLoadView.initLoad();//自动加载一定要设置setPullCallback()后面...不然不会自动加载
        mRecyclerView = mPullToLoadView.getRecyclerView();
        mHomeRecycleViewAdapter = new HomeRecycleViewAdapter(this, activityList);
        //类似墙砖风格Grid布局（水平方向错开不对齐）
        mGridLayoutManager = new GridLayoutManager(MainActivity.this, 3);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //判断是不是头部还是尾部，头部、尾部不作处理
                if (mHomeRecycleViewAdapter.isHeaderView(position) || mHomeRecycleViewAdapter.isFooterView(position)) {
                    //返回设定的列数长度（全占）
                    return mGridLayoutManager.getSpanCount();
                }
                //计算对应算法...
                else if ((position % 4) == 2 || (position % 4) == 3) {
                    //返回2列宽的条目
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mHomeRecycleViewAdapter);
        mRecyclerView.addOnItemTouchListener(new OnRecycleItemClickListener(mRecyclerView) {
            @Override
            public void OnItemClick(RecyclerView.ViewHolder holder) {
                if (holder.getItemViewType() == HomeRecycleViewAdapter.ITEM_TYPE_HEADER) {
                    TextView textView = (TextView) ((CardView) holder.itemView).findViewById(R.id.headText);
                    ToastUtils.showShort( textView.getText().toString());
                } else {
                    if (holder.getItemViewType() == HomeRecycleViewAdapter.ITEM_TYPE_CONTENT) {
                        openNewActivity(activityList.get(holder.getPosition() - HomeRecycleViewAdapter.mHeaderCount).getName(), activityList.get(holder.getPosition() - HomeRecycleViewAdapter.mHeaderCount).getActivity());
                    } else if (holder.getItemViewType() == HomeRecycleViewAdapter.ITEM_TYPE_FOOTER) {
                        TextView textView = (TextView) ((CardView) holder.itemView).findViewById(R.id.footerText);
                        ToastUtils.showShort(textView.getText().toString());
                    }
                }
            }

            @Override
            public void onLongItemClick(RecyclerView.ViewHolder holder) {
                super.onLongItemClick(holder);
                ToastUtils.setBgColor(Color.RED);
                ToastUtils.setMsgColor(Color.WHITE);
                ToastUtils.showShort("测试" + holder.getPosition());
            }
        });
    }



    private void addItemData() {
        for (int i = 0; i < 10; i++) {
            activityList.add(new ActivityParameter(0, "RxAndroid", RxAndroidActivity.class));
            activityList.add(new ActivityParameter(1, "GreenDao3.2", GreenDaoActivity.class));
            activityList.add(new ActivityParameter(2, "页面首次显示使用引导", NewbieGuideActivity.class));
            activityList.add(new ActivityParameter(4, "NiceDialog", NiceDialogActivity.class));
        }
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
                if (page > 4) {
                    isHasLoadedAll = true;
                    mPullToLoadView.setComplete();
                    return;
                }
                //切记：采用此控件在刷新的时候，一定要回调成功后再将旧的数据清除，不能在请求新数据前（在onRefresh方法中）先清除数据；
                if (page == 1) {
                    activityList.clear();
                }
                //更换数据
                addItemData();
                mPullToLoadView.setComplete();//关闭加载框
                isLoading = false;
                nextPage = page + 1;
                mHomeRecycleViewAdapter.notifyDataSetChanged();
            }
        }, 1500);
    }

    /**
     * 启动activity
     * @param activityTitleName
     * @param desClass
     */
    private void openNewActivity(String activityTitleName , Class<? extends AppCompatActivity> desClass){
        Bundle bundle = new Bundle();
        bundle.putString(ACTIVITY_TITLE_NAME, activityTitleName);
        ActivityUtils.startActivity(bundle, MainActivity.this, desClass);
    }


    /***********发下四个方法是PullCallback接口所实现的方法*******/
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
        //表示正在加载中...
        return isLoading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        //表示所有页面已经加载完毕
        return isHasLoadedAll;
    }


    //200ms内点击两次才调用返回
    long mExitTime = 0;
    @Override
    public void onBackPressed() {
        //1.点击的时间差如果大于2000，则提示用户点击两次退出
        if(System.currentTimeMillis() - mExitTime > 2000) {
            //2.保存当前时间
            mExitTime  = System.currentTimeMillis();
            //3.提示
            ToastUtils.showShort("再点击一次后退出...");
        } else {
            //4.点击的时间差小于2000，调用父类onBackPressed方法执行退出。
            super.onBackPressed();
        }
    }
}
