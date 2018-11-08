package com.linjiawei.mytestdemo.rxandroid.fragment.function;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.linjiawei.mytestdemo.OnRecycleItemClickListener;
import com.linjiawei.mytestdemo.R;
import com.linjiawei.mytestdemo.base.RxFragmentV4;
import com.linjiawei.mytestdemo.interfacebase.OnFragmentInteractionListener;
import com.linjiawei.mytestdemo.javabean.RxFunctionBean;
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

    private List<RxFunctionBean> RxFunctionBeans = new ArrayList<>();

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
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addRxListTitle();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(new CommonAdapter<RxFunctionBean>(getContext(), R.layout.rv_item, RxFunctionBeans) {
            @Override
            protected void convert(ViewHolder holder, RxFunctionBean s, int position) {
                holder.setText(R.id.tv_item_text, s.getFunctionTitle() + " : \n" + s.getDescribe());
            }
        });
        mRecyclerView.addOnItemTouchListener(new OnRecycleItemClickListener(mRecyclerView) {
            @Override
            public void OnItemClick(RecyclerView.ViewHolder holder) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(RxFunctionExerciseActivity.DATA_TAG, RxFunctionBeans.get(holder.getAdapterPosition()));
                ActivityUtils.startActivity(bundle, RxFunctionExerciseActivity.class);
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
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_create), "用于创建一个被观察的对象"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_zip), "合并事件专用," +
                "分别从两个上游事件中各取出一个组合," +
                "一个事件只能被使用一次，顺序严格按照事件发送的顺序," +
                "最终下游事件收到的是和上游事件最少的数目相同（必须两两配对，多余的舍弃)"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_map), "基本是RxJava 最简单的操作符了作用是对上游发送的每一个事件应用一个函数，" +
                "使得每一个事件都按照指定的函数去变化"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_flatMap), "FlatMap将一个发送事件的上游Observable变换成多个发送事件的Observables， 然后将它们发射的时间合并后放进一个单独的Observable里"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_concatMap), "concatMap作用和flatMap几乎一模一样，唯一的区别是它能保证事件的顺序"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_switchMap), "switchMap作用和flatMap差不多，但switchMap操作符只会发射[emit]最近的Observables。"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_doOnNext), "让订阅者在接收到数据前干点事情的操作符"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_filter), "过滤操作符，取正确的值"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_skip), "接受一个long型参数，代表跳过多少个数目的事件再开始接收"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_take), "用于指定订阅者最多收到多少数据"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_timer), "在Rxjava中timer 操作符既可以延迟执行一段逻辑，也可以间隔执行一段逻辑\n" +
                "【注意】但在RxJava 2.x已经过时了，现在用interval操作符来间隔执行，详见RxIntervalActivity\n" +
                "timer和interval都默认执行在一个新线程上。"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_interval), "间隔执行操作，默认在新线程"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_just), "just操作符，和RxJava 1.x 没有什么区别，就是接受一个可变参数，依次发送"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_single), "顾名思义，Single只会接收一个参数" +
                "而SingleObserver只会调用onError或者onSuccess"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_concat), "连接操作符，可接受Observable的可变参数，或者Observable的集合"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_distinct), "去重操作符，其实就是简单的去重"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_buffer), "buffer(count, skip)` 从定义就差不多能看出作用了，将 observable 中的数据按 skip（步长）分成最长不超过 count 的 buffer，然后生成一个 observable"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_debounce), "过滤掉发射速率过快的数据项"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_defer), "就是在每次订阅的时候就会创建一个新的 Observable"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_last), "取出最后一个值，参数是没有值的时候的默认值"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_merge), "将多个Observable合起来，接受可变参数，也支持使用迭代器集合"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_reduce), "就是一次用一个方法处理一个值，可以有一个seed作为初始值"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_scan), "和上面的reduce差不多，区别在于reduce()只输出结果，而scan()会将过程中每一个结果输出"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_window), "按照时间划分窗口，将数据发送给不同的Observable"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_PublishSubject), "onNext() 会通知每个观察者，仅此而已"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_AsyncSubject), "在调用 onComplete() 之前，除了 subscribe() 其它的操作都会被缓存，在调用 onComplete() 之后只有最后一个 onNext() 会生效"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_BehaviorSubject), "BehaviorSubject 的最后一次 onNext() 操作会被缓存，然后在 subscribe() 后立刻推给新注册的 Observer"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_Completable), "只关心结果，也就是说 Completable 是没有 onNext 的，要么成功要么出错，不关心过程，在 subscribe 后的某个时间点返回结果"));
        RxFunctionBeans.add(new RxFunctionBean(getString(R.string.rx_Flowable), "专用于解决背压问题"));
    }


}
