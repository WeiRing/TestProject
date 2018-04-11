package com.linjiawei.mytestdemo.rxandroid.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.blankj.utilcode.util.ToastUtils;
import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.RxFragmentV4;
import com.linjiawei.mytestdemo.interfacebase.OnFragmentInteractionListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


public class RxCheckBoxUpdateFragment extends RxFragmentV4 {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.checkbox1)
    CheckBox mCheckbox1;
    @Bind(R.id.checkbox2)
    CheckBox mCheckbox2;
    @Bind(R.id.rxNextBtn)
    Button mRxNextBtn;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static RxCheckBoxUpdateFragment newInstance(String param1, String param2) {
        RxCheckBoxUpdateFragment fragment = new RxCheckBoxUpdateFragment();
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
        View view = inflater.inflate(R.layout.fragment_rx_check_box_update, container, false);
        ButterKnife.bind(this, view);
        RememberToSharePreference();
        changeCheckBoxToUpdateView();
        return view;
    }

    /**
     * 同步记录SharePreference状态到checkBox
     */
    public void RememberToSharePreference(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        RxSharedPreferences rxPreferences = RxSharedPreferences.create(preferences);
        Preference<Boolean> myPreFunction = rxPreferences.getBoolean("selectState", false);
        mCheckbox1.setChecked(myPreFunction.get());
        //checkBox的状态跟SharedPreferences同步联动
        RxCompoundButton.checkedChanges(mCheckbox1).subscribe(myPreFunction.asConsumer());
    }

    /**
     * 改变复选框来更新视图
     */
    public void changeCheckBoxToUpdateView(){
        /*RxCompoundButton.checkedChanges(mCheckbox2).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                mRxNextBtn.setClickable(aBoolean);
                mRxNextBtn.setBackgroundResource(aBoolean ? R.color.colorAccent : R.color.not_next);
            }
        });*/

        //2.0用法
        RxCompoundButton.checkedChanges(mCheckbox2).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                mRxNextBtn.setClickable(aBoolean);
                mRxNextBtn.setBackgroundResource(aBoolean ? R.color.colorAccent : R.color.not_next);
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

    @OnClick(R.id.rxNextBtn)
    public void onClick() {
        ToastUtils.showShort("下一步");
    }
}
