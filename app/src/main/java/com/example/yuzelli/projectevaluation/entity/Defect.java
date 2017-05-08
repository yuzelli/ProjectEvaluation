package com.example.yuzelli.projectevaluation.entity;

import java.io.Serializable;

/**
 * Created by 51644 on 2017/5/4.
 */

public class Defect implements Serializable {


    /**
     * d_state : 2
     * d_reason : 测试2
     * d_type : 2
     * d_time : 2017-06-05
     * d_number : 2
     * defect_id : 6
     * d_project_id : 4
     * d_consequence : 测试2
     */

    private int d_state;
    private String d_reason;
    private int d_type;
    private String d_time;
    private int d_number;
    private int defect_id;
    private int d_project_id;
    private String d_consequence;

    public int getD_state() {
        return d_state;
    }

    public void setD_state(int d_state) {
        this.d_state = d_state;
    }

    public String getD_reason() {
        return d_reason;
    }

    public void setD_reason(String d_reason) {
        this.d_reason = d_reason;
    }

    public int getD_type() {
        return d_type;
    }

    public void setD_type(int d_type) {
        this.d_type = d_type;
    }

    public String getD_time() {
        return d_time;
    }

    public void setD_time(String d_time) {
        this.d_time = d_time;
    }

    public int getD_number() {
        return d_number;
    }

    public void setD_number(int d_number) {
        this.d_number = d_number;
    }

    public int getDefect_id() {
        return defect_id;
    }

    public void setDefect_id(int defect_id) {
        this.defect_id = defect_id;
    }

    public int getD_project_id() {
        return d_project_id;
    }

    public void setD_project_id(int d_project_id) {
        this.d_project_id = d_project_id;
    }

    public String getD_consequence() {
        return d_consequence;
    }

    public void setD_consequence(String d_consequence) {
        this.d_consequence = d_consequence;
    }
}
