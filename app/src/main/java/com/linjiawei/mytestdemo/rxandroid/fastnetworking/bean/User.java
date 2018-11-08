package com.linjiawei.mytestdemo.rxandroid.fastnetworking.bean;

import java.io.Serializable;

/**
 * Created by LinJiawei on 2018/5/30. 17:00
 * mail:911926881@qq.com
 */

public class User implements Serializable{

    /**
     * id : 1
     * firstname : Amit
     * lastname : Shekhar
     */

    private int id;
    private String firstname;
    private String lastname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
