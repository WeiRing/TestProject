package com.linjiawei.mytestdemo.rxandroid.fastnetworking;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ConnectionQuality;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ConnectionQualityChangeListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.widget.ANImageView;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.linjiawei.mytestdemo.OnRecycleItemClickListener;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.ToolbarBaseActivity;
import com.linjiawei.mytestdemo.rxandroid.fastnetworking.bean.User;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 此案例参考：http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2016/1008/6663.html
 */
public class FastNetworkingTestActivity extends ToolbarBaseActivity {
    @Bind(R.id.fastNetRecycleView)
    RecyclerView mRxAndroidRecycleView;
    @Bind(R.id.fastNetTextView)
    TextView mTextView;
    @Bind(R.id.fastImageView)
    ANImageView mANImageView;

    private List<String> dataList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_fast_networking_test;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setTitle(getIntent().getExtras().getString(ACTIVITY_TITLE_NAME));
        AndroidNetworking.setConnectionQualityChangeListener(new ConnectionQualityChangeListener() {
            @Override
            public void onChange(ConnectionQuality currentConnectionQuality, int currentBandwidth) {
                ToastUtils.showShort("网络质量：" + currentConnectionQuality.toString() + "\n" + "当前带宽：" + currentBandwidth);
            }
        });
        addItem();
        mANImageView.setDefaultImageResId(R.mipmap.ic_launcher);
        mANImageView.setErrorImageResId(R.mipmap.download);
        mRxAndroidRecycleView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRxAndroidRecycleView.setAdapter(new CommonAdapter<String>(this, R.layout.rv_item, dataList) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
            }

            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.tv_item_text, s);
            }
        });
        mRxAndroidRecycleView.addOnItemTouchListener(new OnRecycleItemClickListener(mRxAndroidRecycleView) {
            @Override
            public void OnItemClick(RecyclerView.ViewHolder holder) {
                mTextView.setText("");

                switch (holder.getAdapterPosition()) {
                    case 0:
                        Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllUsers/{pageNumber}")
                                .addPathParameter("pageNumber", "0")
                                .addQueryParameter("limit", "3")
                                .setTag(this)
                                .build()
                                .getObjectListObservable(User.class)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<List<User>>() {
                                    @Override
                                    public void accept(List<User> users) throws Exception {
                                        LogUtils.i("GETRequest", "data:" + users.toString());
                                        mTextView.setText(users.toString());
                                        ToastUtils.showLong(users.toString());
                                    }
                                });

                        Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllUsers/{pageNumber}")
                                .addPathParameter("pageNumber", "0")
                                .addQueryParameter("limit", "3")
                                .setTag(this)
                                .build()
                                .getAsString(new StringRequestListener() {
                                    @Override
                                    public void onResponse(String response) {

                                    }

                                    @Override
                                    public void onError(ANError anError) {

                                    }
                                });







                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

                        break;
                    case 8:

                        break;
                    case 9:
                        //Note : If bytesSent or bytesReceived is -1 , it means it is unknown
                        ConnectionQuality currentConnectionQuality = AndroidNetworking.getCurrentConnectionQuality();
                        ToastUtils.showShort("网络质量：" + currentConnectionQuality.toString() + "\n" + "当前带宽：" + AndroidNetworking.getCurrentBandwidth());
                        break;
                    case 10:

                        break;
                    case 11:

                        break;
                }
            }
        });

    }

    /**
     * 添加选项
     */
    private void addItem() {
        dataList.add("GET请求");//0
        dataList.add("POST请求");//1
        dataList.add("和JAVA对象一起使用 - Getting an user");//2
        dataList.add("和JAVA对象一起使用 - Getting the userList");//3
        dataList.add("从服务器download一个文件");//4
        dataList.add("upload文件服务器");//5
        dataList.add("得到一个响应并在另一个线程executor中完成");//6
        dataList.add("把从网络加载的图片放到ImageView");//7
        dataList.add("从url获取指定了一些参数的Bitmap");//8
        dataList.add("用ConnectionClass Listener得到当前网络质量和带宽");//9
        dataList.add("监听请求过程的数据信息Analytics");//10
        dataList.add("指定了tag的请求都可以被取消");//11
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AndroidNetworking.removeConnectionQualityChangeListener();
    }

}
