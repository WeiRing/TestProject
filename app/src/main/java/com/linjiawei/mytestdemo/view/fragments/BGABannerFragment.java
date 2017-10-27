package com.linjiawei.mytestdemo.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.interfacebase.OnFragmentInteractionListener;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;


/**
 * 自动滚动广告栏
 * 接入的5个步骤:https://github.com/bingoogolapple/BGABanner-Android#%E5%8A%9F%E8%83%BD%E4%BB%8B%E7%BB%8D
 */

public class BGABannerFragment extends RxFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    @Bind(R.id.banner_guide_content)
    BGABanner mBannerGuideContent;

    public static BGABannerFragment newInstance(String param1, String param2) {
        BGABannerFragment fragment = new BGABannerFragment();
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
        View view = inflater.inflate(R.layout.fragment_bgabanner, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBannerGuideContent.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                Glide.with(BGABannerFragment.this)
                        .load(model)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .centerCrop()
                        .dontAnimate()
                        .into(itemView);
            }
        });
        mBannerGuideContent.setData(Arrays.asList("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=134408327,235874462&fm=58&bpow=640&bpoh=480", "https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=2179909764,2895522491&fm=85&s=4CE6A74425FB2025781041140300C0C2", "https://ss0.baidu.com/73x1bjeh1BF3odCf/it/u=2033816434,820626626&fm=85&s=D23E3CC4D6D9912E31101C790300C050"), Arrays.asList("提示文字1", "提示文字2", "提示文字3"));

        //监听广告 item 的单击事件，在 BGABanner 里已经帮开发者处理了防止重复点击事件

        mBannerGuideContent.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                Toast.makeText(banner.getContext(), "点击了" + position, Toast.LENGTH_SHORT).show();
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
