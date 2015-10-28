package com.eland.elandandroidframework.JavaBean;

import cn.bmob.v3.BmobUser;

/**
 * Created by elandmac on 15/10/15.
 */
public class MyUser extends BmobUser {

    //扩展BmobUser类
    private String sex;
    private Integer age;

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

