package com.linjiawei.mytestdemo.rxandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.RxFragmentV4;
import com.linjiawei.mytestdemo.interfacebase.OnFragmentInteractionListener;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class RxLoopFragment extends RxFragmentV4 {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.rxLooperImage)
    ImageView mRxLooperImage;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Disposable subscribe;
    private int[] imagePath = {R.drawable.pic_1, R.drawable.pic_2, R.drawable.pic_3};
    private int currentIndex = 0;

    public static RxLoopFragment newInstance(String param1, String param2) {
        RxLoopFragment fragment = new RxLoopFragment();
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
        View view = inflater.inflate(R.layout.fragment_rx_loop, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 开始轮询
     */
    @OnClick(R.id.startLooperBtn)
    public void startLooperClick() {
        if (subscribe == null || subscribe.isDisposed()) {
            subscribe = Observable.interval(500, 3000, TimeUnit.MILLISECONDS)
                    //延时500开始执行 ，每间隔3000，时间单位
                    .compose(this.<Long>bindToLifecycle())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(@NonNull Long aLong) throws Exception {
                            int index = currentIndex % imagePath.length;
                            Glide.with(RxLoopFragment.this).load(imagePath[index]).into(mRxLooperImage);
                            currentIndex++;
                        }
                    });
        }
    }

    /**
     * 停止轮询
     */
    @OnClick(R.id.stopLooperBtn)
    public void stopLooperClick() {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
            ToastUtils.showShort("停止更新...");
        }
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
        stopLooperClick();
    }

}
