package com.example.rxandroiddemo.request.model;

/**
 * Created by LinJiawei on 2017/3/17. 16:23
 */
public class PostModel {
    private int code;
    private String message;
    private XgoEntity entity;

    private class XgoEntity {
        private Data data;

        private class Data {
            private String xxx;

            @Override
            public String toString() {
                return "Data{" +
                        "xxx='" + xxx + '\'' +
                        '}';
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
        return "GetModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", entity=" + entity +
                '}';
    }
}
