package com.linjiawei.mytestdemo.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.RxFragmentV4;
import com.linjiawei.mytestdemo.interfacebase.OnFragmentInteractionListener;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.linjiawei.mytestdemo.R.id.openSystemImageBtn;

public class RxImagePickerFragment extends RxFragmentV4 implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(openSystemImageBtn)
    Button mOpenSystemImage;
    @Bind(R.id.takePhotoBtn)
    Button mTakePhoto;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;

    public static RxImagePickerFragment newInstance(String param1, String param2) {
        RxImagePickerFragment fragment = new RxImagePickerFragment();
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
        View inflateView = inflater.inflate(R.layout.fragment_rx_image_picker, container, false);
        ButterKnife.bind(this, inflateView);
        return inflateView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mOpenSystemImage.setOnClickListener(this);
        mTakePhoto.setOnClickListener(this);
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
    public void onClick(View view) {
        if (view.getId() == R.id.openSystemImageBtn) {
            ToastUtils.showShort("openSystemImageBtn");
        } else if (view.getId() == R.id.takePhotoBtn) {
            ToastUtils.showShort("takePhotoBtn");
        }




    }
}
