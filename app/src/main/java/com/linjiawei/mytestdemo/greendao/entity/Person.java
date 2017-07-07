package com.linjiawei.mytestdemo.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by LinJiawei on 2017/7/6. 17:41
 * mail:911926881@qq.com
 */

@Entity
public class Person {
    @Id(autoincrement = true)
    private long id;
    private long IDNumber;
    private String name;
    private int age;
    private boolean sex;
    @Generated(hash = 1062888475)
    public Person(long id, long IDNumber, String name, int age, boolean sex) {
        this.id = id;
        this.IDNumber = IDNumber;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
    @Generated(hash = 1024547259)
    public Person() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getIDNumber() {
        return this.IDNumber;
    }
    public void setIDNumber(long IDNumber) {
        this.IDNumber = IDNumber;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public boolean getSex() {
        return this.sex;
    }
    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
