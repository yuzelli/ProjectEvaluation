package com.example.yuzelli.projectevaluation.entity;

import java.io.Serializable;

/**
 * Created by 51644 on 2017/5/3.
 */

public class UserInfo implements Serializable{

    /**
     * u_name : abc
     * u_passWord : 123123
     * u_phone : 13133443006
     * u_type : 1
     * user_id : 1
     */

    private String u_name;
    private String u_passWord;
    private String u_phone;
    private int u_type;
    private int user_id;

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_passWord() {
        return u_passWord;
    }

    public void setU_passWord(String u_passWord) {
        this.u_passWord = u_passWord;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public int getU_type() {
        return u_type;
    }

    public void setU_type(int u_type) {
        this.u_type = u_type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
