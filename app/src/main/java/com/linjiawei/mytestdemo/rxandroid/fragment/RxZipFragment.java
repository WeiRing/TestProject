package com.linjiawei.mytestdemo.rxandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;
import com.linjiawei.mytestdemo.OnRecycleItemClickListener;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.RxFragmentV4;
import com.linjiawei.mytestdemo.interfacebase.OnFragmentInteractionListener;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxZipFragment extends RxFragmentV4 {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.rxZipGetDataBtn)
    Button mRxZipGetDataBtn;
    @Bind(R.id.rxZipRecycleView)
    RecyclerView mRxZipRecycleView;
    CommonAdapter<String> adapter;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private List<String> dateLists = new ArrayList<>();
    BaseNiceDialog loadingDialog;

    public static RxZipFragment newInstance(String param1, String param2) {
        RxZipFragment fragment = new RxZipFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rx_zip, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    public void initView() {
//        for (int i = 0; i < 5; i++) {
//            dateLists.add("本地测试数据" + i);
//        }
        mRxZipRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CommonAdapter<String>(getContext(), R.layout.list_data_item, dateLists) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.list_item_text, dateLists.get(position));
            }
        };
        mRxZipRecycleView.setAdapter(adapter);

        mRxZipRecycleView.addOnItemTouchListener(new OnRecycleItemClickListener(mRxZipRecycleView) {
            @Override
            public void OnItemClick(RecyclerView.ViewHolder holder) {
                ToastUtils.showShort(dateLists.get(holder.getAdapterPosition()));
            }
        });
    }

    @OnClick(R.id.rxZipGetDataBtn)
    public void onClick() {
        showLoadingDialog();
        Observable
                .zip(queryContactsFromLocation(), queryContactsForNet(), new BiFunction<List<String>, List<String>, List<String>>() {
                    @Override
                    public List<String> apply(@NonNull List<String> strings, @NonNull List<String> strings2) throws Exception {
                        //两个子线程任务都执行完才回调些方法：queryContactsFromLocation(), queryContactsForNet()
                        dateLists.addAll(strings);
                        dateLists.addAll(strings2);
                        return strings;
                    }
                }).compose(this.<List<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(@NonNull List<String> strings) throws Exception {
                        loadingDialog.dismiss();
                        adapter.notifyDataSetChanged();
                    }
                });
                /*.zip(queryContactsFromLocation(), queryContactsForNet(), new Func2<List<String>, List<String>, List<String>>() {
                    @Override
                    public List<String> call(List<String> strings, List<String> strings2) {
                        //两个子线程任务都执行完才回调些方法：queryContactsFromLocation(), queryContactsForNet()
                        dateLists.addAll(strings);
                        dateLists.addAll(strings2);
                        return strings;
                    })
       }).compose(this.<List<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> contacters) {
                        loadingDialog.dismiss();
                        adapter.notifyDataSetChanged();
                    }
                });*/
    }


    /**
     * 模拟手机本地联系人查询
     */
    private Observable<List<String>> queryContactsFromLocation() {

        return Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<String>> e) throws Exception {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
                ArrayList<String> contacters = new ArrayList<>();
                contacters.add("本地联系人：张三");
                contacters.add("本地联系人：李四");
                contacters.add("本地联系人：王五");
                contacters.add("本地联系人：赵六");
                ToastUtils.showShort("本地联系人加载完成...");
                e.onNext(contacters);
                e.onComplete();
            }
        });

        /*return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ArrayList<String> contacters = new ArrayList<>();
                contacters.add("本地联系人：张三");
                contacters.add("本地联系人：李四");
                contacters.add("本地联系人：王五");
                contacters.add("本地联系人：赵六");
                ToastUtils.showShort("本地联系人加载完成...");
                subscriber.onNext(contacters);
                subscriber.onComplete();
            }
        });*/
    }

    /**
     * 模拟网络联系人列表
     */
    private Observable<List<String>> queryContactsForNet() {
        return Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<String>> e) throws Exception {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
                ArrayList<String> contacters = new ArrayList<>();
                contacters.add("云盘上的联系人：老大");
                contacters.add("云盘上的联系人：老二");
                contacters.add("云盘上的联系人：老三");
                contacters.add("云盘上的联系人：老四");
                ToastUtils.showShort("云盘联系人加载完成...");
                e.onNext(contacters);
                e.onComplete();
            }
        });
        /*return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ArrayList<String> contacters = new ArrayList<>();
                contacters.add("云盘上的联系人：老大");
                contacters.add("云盘上的联系人：老二");
                contacters.add("云盘上的联系人：老三");
                contacters.add("云盘上的联系人：老四");
                ToastUtils.showShort("云盘联系人加载完成...");
                subscriber.onNext(contacters);
                subscriber.onCompleted();
            }
        });*/
    }

    /**
     * 加载框
     */
    public void showLoadingDialog() {
        loadingDialog = NiceDialog.init()
                .setLayoutId(R.layout.nice_dialog_loading_layout)
                .setWidth(150)
                .setHeight(150)
                .setDimAmount(0.5f)//控制加载框背景是否变暗/透明
                .setOutCancel(false)
                .show(getFragmentManager());
    }


    public void onButtonPressed(int type) {
        if (mListener != null) {
            mListener.onFragmentInteraction(type);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}
