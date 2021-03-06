package com.linjiawei.mytestdemo.rxandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.RxFragmentV4;
import com.linjiawei.mytestdemo.interfacebase.OnFragmentInteractionListener;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class RxConcatFragment extends RxFragmentV4 {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    @Bind(R.id.concatText)
    TextView mConcatText;
    private BaseNiceDialog loadingDialog;

    public static RxConcatFragment newInstance(String param1, String param2) {
        RxConcatFragment fragment = new RxConcatFragment();
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
        View view = inflater.inflate(R.layout.fragment_rx_concat, container, false);
        ButterKnife.bind(this, view);
        concatDemo();
        showLoadingDialog();
        return view;
    }


    private void concatDemo() {
        Observable.concat(getLocalCacheData(), getNetData())
                .compose(this.<String>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mConcatText.setText(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                        ToastUtils.showShort("onCompleted");
                    }
                });


        //1.0写法
                /*.subscribe(new Action1<String>() {
                    @Override
                    public void call(String contacters) {
                        mConcatText.setText(contacters);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                        ToastUtils.showShort("onCompleted");
                    }
                });*/
    }

    /**
     * 模拟提取本地缓存
     */
    private Observable<String> getLocalCacheData() {
        /*Observable<String> localObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //取到本地缓存的数据
                String localData = "这是本地缓存的数据";
                subscriber.onNext(localData);
                subscriber.onCompleted();

            }
        });*/

        Observable<String> localObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
                //取到本地缓存的数据
                String localData = "这是本地缓存的数据";
                e.onNext(localData);
                e.onComplete();
            }
        });
        return localObservable;
    }

    /**
     * 模拟加载网络数据
     */
    private Observable<String> getNetData() {
        /*final Observable<String> netObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String netData = "这是网络上加载到的数据...";
                subscriber.onNext(netData);
                subscriber.onCompleted();
            }
        });*/
        final Observable<String> netObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
                String netData = "这是网络上加载到的数据...";
                e.onNext(netData);
                e.onComplete();
            }
        });
        return netObservable;
    }

    /**
     * 加载框
     */
    public void showLoadingDialog() {
        loadingDialog = NiceDialog.init()
                .setLayoutId(R.layout.nice_dialog_loading_layout)
                .setWidth(100)
                .setHeight(100)
                .setDimAmount(0)
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
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
