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

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class RxBufferFragment extends RxFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.bufferBtn)
    Button mBufferBtn;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static RxBufferFragment newInstance(String param1, String param2) {
        RxBufferFragment fragment = new RxBufferFragment();
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
        View view = inflater.inflate(R.layout.fragment_rx_buffer, container, false);
        ButterKnife.bind(this, view);
        clickThreeCount();
        return view;
    }

    private void clickThreeCount(){
        RxView.clicks(mBufferBtn).buffer(3)
                .compose(this.<List<Void>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Void>>() {
                    @Override
                    public void call(List<Void> voids) {
                        ToastUtils.showShort("已经累积点了3次...");
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
