package com.linjiawei.greendaodemo.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by : 林嘉伟  Date：2017/2/27
 */
@Entity
public class User {
    @Id(autoincrement = true)
    private long id;
    private String userName;
    private boolean sex;
    private int number;
    @Generated(hash = 1185163541)
    public User(long id, String userName, boolean sex, int number) {
        this.id = id;
        this.userName = userName;
        this.sex = sex;
        this.number = number;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public boolean getSex() {
        return this.sex;
    }
    public void setSex(boolean sex) {
        this.sex = sex;
    }
    public int getNumber() {
        return this.number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", sex=" + sex +
                ", number=" + number +
                '}';
    }
}
