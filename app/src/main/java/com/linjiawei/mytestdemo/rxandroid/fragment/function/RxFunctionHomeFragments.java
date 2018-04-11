package com.linjiawei.mytestdemo.rxandroid.fragment.function;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.linjiawei.mytestdemo.OnRecycleItemClickListener;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.RxFragmentV4;
import com.linjiawei.mytestdemo.interfacebase.OnFragmentInteractionListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class RxFunctionHomeFragments extends RxFragmentV4 {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    @Bind(R.id.rxFunctionRecycleView)
    RecyclerView mRecyclerView;

    private List<String> titleNames = new ArrayList<>();

    public static RxFunctionHomeFragments newInstance(String param1, String param2) {
        RxFunctionHomeFragments fragment = new RxFunctionHomeFragments();
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
        View view = inflater.inflate(R.layout.fragment_rx_function_home_fragments, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addRxListTitle();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(new CommonAdapter<String>(getContext(), R.layout.rv_item, titleNames) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_item_text, s);
            }
        });
        mRecyclerView.addOnItemTouchListener(new OnRecycleItemClickListener(mRecyclerView) {
            @Override
            public void OnItemClick(RecyclerView.ViewHolder holder) {
                ToastUtils.showShort(titleNames.get(holder.getAdapterPosition()));
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

    private void addRxListTitle() {
        titleNames.add("Create --> 用于获取一个被观察的对象");
        titleNames.add("Create --> 用于获取一个被观察的对象2");
        titleNames.add("Create --> 用于获取一个被观察的对象3");
        titleNames.add("Create --> 用于获取一个被观察的对象4");
    }


}
