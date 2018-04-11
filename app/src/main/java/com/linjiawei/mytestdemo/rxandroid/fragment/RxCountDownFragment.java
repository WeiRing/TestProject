package com.linjiawei.mytestdemo.rxandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.RxFragmentV4;
import com.linjiawei.mytestdemo.interfacebase.OnFragmentInteractionListener;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


/**
 * 倒计时功能
 *  compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
 *  compile 'io.reactivex.rxjava2:rxjava:2.0.1'
 *
 */
public class RxCountDownFragment extends RxFragmentV4 {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    @Bind(R.id.btn_send_sms)
    Button requestCodeBtn;
    @Bind(R.id.btn_sms_submit)
    Button sendCodeBtn;

    public static RxCountDownFragment newInstance(String param1, String param2) {
        RxCountDownFragment fragment = new RxCountDownFragment();
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
        View view = inflater.inflate(R.layout.fragment_rx_count_down, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //获取短信按钮
        RxView.clicks(requestCodeBtn).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                if (mObservable == null) {
                    initCountDown2();
                    mObservable.subscribe(mObserver);
                } else {
                    mObservable.subscribe(mObserver);
                }
            }
        });

        //1.0写法
        /*.subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (mObservable == null) {
                    initCountDown2();
                    mObservable.subscribe(mObserver);
                } else {
                    mObservable.subscribe(mObserver);
                }
            }
        });*/


        //提交按钮
        RxView.clicks(sendCodeBtn)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        if (mDisposable != null) {
                            mDisposable.dispose();
                            requestCodeBtn.setEnabled(true);
                            requestCodeBtn.setText("发送短信");
                        }
                    }
                });
               /* .subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (mDisposable != null) {
                    mDisposable.dispose();
                    requestCodeBtn.setEnabled(true);
                    requestCodeBtn.setText("发送短信");
                }
            }
        });*/
    }


    private static final long MAX_COUNT_TIME = 10;
    private Disposable mDisposable;
    private Observer mObserver;
    private Observable mObservable;

    /**
     * RxJava 方式实现
     * 第一种方式
     */
    public void initCountDown() {
        //它在指定延迟之后先发射一个零值，然后再按照指定的时间间隔发射递增的数字,设置0延迟，每隔1000毫秒发送一条数据
        mObservable = Observable.interval(1, TimeUnit.SECONDS)
                .take( MAX_COUNT_TIME + 1)//设置总共发送的次数,续1s
                .map(new Function<Long, Long>() {//数据转换 long 值是从0到最大，倒计时需要将值倒置
                    @Override
                    public Long apply(Long aLong) throws Exception {//已经过了一秒
                        return MAX_COUNT_TIME - aLong - 1;
                    }
                })
                .subscribeOn(Schedulers.computation())
                .doOnSubscribe(new Consumer<Disposable>() {//执行计时任务前先将 button 设置为不可点击
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        requestCodeBtn.setEnabled(false);
                        requestCodeBtn.setText(MAX_COUNT_TIME + "s");
                        Log.e(TAG, "disposable ");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());//显示放在主线程。

        mObserver = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(Long value) {
                requestCodeBtn.setText(value + "s");
                Log.e(TAG, "value : " + value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                requestCodeBtn.setEnabled(true);
                requestCodeBtn.setText("发送短信");
            }
        };
    }


    public static int SHOW_COUNT = 0;
    public static int COUNT = 62;//倒计时总秒数 + 2 = 执行的总次数(总时间是61s,但要显示62次)
    /**
     * 第二种方式
     */
    public void initCountDown2(){
        //它在指定延迟0s之后先发射一个值2，然后再按照指定的时间间隔发射递增的数字，每隔1000毫秒发送一条数据
        mObservable = Observable.intervalRange(2, COUNT, 0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, Object>() {
                    @Override
                    public Object apply(Long aLong) throws Exception {
                        SHOW_COUNT = (int) (COUNT - aLong);
                        return SHOW_COUNT;
                    }
                }).subscribeOn(Schedulers.computation())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //开始订阅时回调
                        requestCodeBtn.setEnabled(false);
                    //    requestCodeBtn.setText(SHOW_COUNT + "s");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());//显示放在主线程。;

        mObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(Integer value) {
                requestCodeBtn.setText(value + " s");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                requestCodeBtn.setEnabled(true);
                requestCodeBtn.setText("发送短信");
            }
        };
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

}
