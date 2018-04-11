package com.linjiawei.mytestdemo.rxandroid.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.RxFragmentV4;
import com.linjiawei.mytestdemo.interfacebase.OnFragmentInteractionListener;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


public class RxTimerFragment extends RxFragmentV4 {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.iv_welcome) ImageView mIvWelcome;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static RxTimerFragment newInstance(String param1, String param2) {
        RxTimerFragment fragment = new RxTimerFragment();
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
        View view = inflater.inflate(R.layout.fragment_rx_timer, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        starTimer();
    }

    private void starTimer() {
        Observable.timer(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<Long>bindToLifecycle())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        // Glide.with(TimerFragment.this).load("http://static.zuchecdn.com/wap/newversion/images/20151225fanli_app.jpg").crossFade().into(iv_welcome);
                        mIvWelcome.setVisibility(View.VISIBLE);
                        ObjectAnimator
                                .ofFloat(mIvWelcome, "alpha", 0.0F, 1.0F)
                                .setDuration(500)
                                .start();
                    }
                });
                /*.subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        // Glide.with(TimerFragment.this).load("http://static.zuchecdn.com/wap/newversion/images/20151225fanli_app.jpg").crossFade().into(iv_welcome);
                        mIvWelcome.setVisibility(View.VISIBLE);
                        ObjectAnimator
                                .ofFloat(mIvWelcome, "alpha", 0.0F, 1.0F)
                                .setDuration(500)
                                .start();
                    }
                });*/
    }


    // TODO: Rename method, update argument and hook method into UI event
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
}
