package com.example.rxandroiddemo.request.model;

import java.util.List;

/**
 * Created by LinJiawei on 2017/3/17. 16:23
 */
public class PutModel {
    private int code;
    private String message;
    private XgoEntity entity;

    private class XgoEntity {
        private List<Data> data;

        private class Data {
            @Override
            public String toString() {
                return "Data{}";
            }
        }

        @Override
        public String toString() {
            return "XgoEntity{" +
                    "data=" + data +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PutModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", entity=" + entity +
                '}';
    }
}
