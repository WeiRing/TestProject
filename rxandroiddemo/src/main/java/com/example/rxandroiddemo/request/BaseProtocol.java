package com.example.rxandroiddemo.request;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * BaseProtocol    加入Gson解析
 * Created by LinJiawei on 2017/3/17. 16:23
 */
public abstract class BaseProtocol {

    private static final Gson mGson;

    static {
        mGson = new Gson();
    }


    /**
     * 创建一个工作在IO线程的被观察者(被订阅者)对象
     *
     * @param url
     * @param method
     * @param params
     */
    protected <T> Observable<T> createObservable(final String url, final String method, final Map<String, Object> params, final Class<T> clazz) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                Request request = XgoHttpClient.getClient().getRequest(url, method, params);
                String json = XgoHttpClient.getClient().execute2String(request);
                setData(subscriber, json, clazz);
            }
        }).subscribeOn(Schedulers.io());
    }


    /**
     * 为观察者（订阅者）设置返回数据
     */
    protected <T> void setData(Subscriber<? super T> subscriber, String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json)) {
            subscriber.onError(new Throwable("not data"));
            return;
        }

        T data = mGson.fromJson(json, clazz);

        subscriber.onNext(data);
        subscriber.onCompleted();
    }
}
