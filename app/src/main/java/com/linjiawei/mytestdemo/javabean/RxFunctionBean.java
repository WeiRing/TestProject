package com.linjiawei.mytestdemo.javabean;

import java.io.Serializable;

/**
 * Created by LinJiawei on 2018/4/11. 15:27
 * mail:911926881@qq.com
 */

public class RxFunctionBean implements Serializable{
    private int count;
    private String functionTitle;
    private String describe;

    public RxFunctionBean(String functionTitle, String describe) {
        this.functionTitle = functionTitle;
        this.describe = describe;
    }

    public RxFunctionBean(int count, String functionTitle, String describe) {
        this.count = count;
        this.functionTitle = functionTitle;
        this.describe = describe;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFunctionTitle() {
        return functionTitle;
    }

    public void setFunctionTitle(String functionTitle) {
        this.functionTitle = functionTitle;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
