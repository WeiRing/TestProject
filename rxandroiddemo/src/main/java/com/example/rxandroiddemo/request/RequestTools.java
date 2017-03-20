package com.example.rxandroiddemo.request;

import com.example.rxandroiddemo.request.model.DeleteModel;
import com.example.rxandroiddemo.request.model.GetModel;
import com.example.rxandroiddemo.request.model.PostModel;
import com.example.rxandroiddemo.request.model.PutModel;

import java.util.Map;

import rx.Observable;

/**
 * Created by LinJiawei on 2017/3/17. 16:23
 * mail:911926881@qq.com
 */

public class RequestTools extends BaseProtocol{

    private static final String BASE_URL = "http://service.test.xgo.com.cn:8080/app/v1/demo/";
    /**
     * Get请求
     */
    public Observable<GetModel> textGet() {
        String path = "1";
        return createObservable(BASE_URL + path, XgoHttpClient.METHOD_GET, null, GetModel.class);
    }


    /**
     * Post请求
     */
    public Observable textPost(Map<String, Object> params) {
        return createObservable(BASE_URL, XgoHttpClient.METHOD_POST, params, PostModel.class);
    }

    /**
     * Put请求
     */
    public Observable<PutModel> textPut(Map<String, Object> params) {
        return createObservable(BASE_URL, XgoHttpClient.METHOD_PUT, params, PutModel.class);
    }

    /**
     * Delete请求
     */
    public Observable<DeleteModel> textDelete() {
        String path = "1";
        return createObservable(BASE_URL + path, XgoHttpClient.METHOD_DELETE, null, DeleteModel.class);
    }




}
