package com.linjiawei.mytestdemo.rxandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.interfacebase.OnFragmentInteractionListener;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class RxBtnNotMoreClickFragment extends RxFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.notFirstMoreBtn)
    Button mNotFirstMoreBtn;
    @Bind(R.id.notLastMoreBtn)
    Button mNotLastMoreBtn;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static RxBtnNotMoreClickFragment newInstance(String param1, String param2) {
        RxBtnNotMoreClickFragment fragment = new RxBtnNotMoreClickFragment();
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
        View view = inflater.inflate(R.layout.fragment_rx_btn_not_more_click, container, false);
        ButterKnife.bind(this, view);
        notMoreClickFirst();
        notMoreClickLast();
        return view;
    }


    private void notMoreClickFirst(){
        RxView.clicks(mNotFirstMoreBtn).throttleFirst(3, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                //3秒内无论点击多少次，只会响应事件1次...
                ToastUtils.showShort("3秒内无论点击多少次，只会响应事件1次...");

            }
        });
    }


    private void notMoreClickLast(){
        RxView.clicks(mNotLastMoreBtn).throttleLast(3, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                //3秒内无论点击多少次，只会响应事件1次...
                ToastUtils.showShort("3秒后再响应事件1次,且3s内只响应一次...");

            }
        });
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
}
