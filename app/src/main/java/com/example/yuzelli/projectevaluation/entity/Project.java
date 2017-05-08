package com.example.yuzelli.projectevaluation.entity;

import java.io.Serializable;

/**
 * Created by 51644 on 2017/5/3.
 */

public class Project implements Serializable {
    private int project_id;
    private String p_name;
    private String p_td_probability;
    private String p_td_serious;
    private String p_td_time;
    private String p_td_min;
    private String p_td_max;
    private String p_md_probability;
    private String p_md_serious;
    private String p_md_time;
    private String p_md_min;
    private String p_md_max;
    private String p_pd_probability;
    private String p_pd_serious;
    private String p_pd_time;
    private String p_pd_min;
    private String p_pd_max;
    private String p_ppqa_probability;
    private String p_ppqa_serious;
    private String p_ppqa_time;
    private String p_ppqa_min;
    private String p_ppaq_max;

    public Project(int project_id, String p_name, String p_td_probability, String p_td_serious, String p_td_time, String p_td_min, String p_td_max, String p_md_probability, String p_md_serious, String p_md_time, String p_md_min, String p_md_max, String p_pd_probability, String p_pd_serious, String p_pd_time, String p_pd_min, String p_pd_max, String p_ppqa_probability, String p_ppqa_serious, String p_ppqa_time, String p_ppqa_min, String p_ppaq_max) {
        this.project_id = project_id;
        this.p_name = p_name;
        this.p_td_probability = p_td_probability;
        this.p_td_serious = p_td_serious;
        this.p_td_time = p_td_time;
        this.p_td_min = p_td_min;
        this.p_td_max = p_td_max;
        this.p_md_probability = p_md_probability;
        this.p_md_serious = p_md_serious;
        this.p_md_time = p_md_time;
        this.p_md_min = p_md_min;
        this.p_md_max = p_md_max;
        this.p_pd_probability = p_pd_probability;
        this.p_pd_serious = p_pd_serious;
        this.p_pd_time = p_pd_time;
        this.p_pd_min = p_pd_min;
        this.p_pd_max = p_pd_max;
        this.p_ppqa_probability = p_ppqa_probability;
        this.p_ppqa_serious = p_ppqa_serious;
        this.p_ppqa_time = p_ppqa_time;
        this.p_ppqa_min = p_ppqa_min;
        this.p_ppaq_max = p_ppaq_max;
    }

    public Project() {
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_td_probability() {
        return p_td_probability;
    }

    public void setP_td_probability(String p_td_probability) {
        this.p_td_probability = p_td_probability;
    }

    public String getP_td_serious() {
        return p_td_serious;
    }

    public void setP_td_serious(String p_td_serious) {
        this.p_td_serious = p_td_serious;
    }

    public String getP_td_time() {
        return p_td_time;
    }

    public void setP_td_time(String p_td_time) {
        this.p_td_time = p_td_time;
    }

    public String getP_td_min() {
        return p_td_min;
    }

    public void setP_td_min(String p_td_min) {
        this.p_td_min = p_td_min;
    }

    public String getP_td_max() {
        return p_td_max;
    }

    public void setP_td_max(String p_td_max) {
        this.p_td_max = p_td_max;
    }

    public String getP_md_probability() {
        return p_md_probability;
    }

    public void setP_md_probability(String p_md_probability) {
        this.p_md_probability = p_md_probability;
    }

    public String getP_md_serious() {
        return p_md_serious;
    }

    public void setP_md_serious(String p_md_serious) {
        this.p_md_serious = p_md_serious;
    }

    public String getP_md_time() {
        return p_md_time;
    }

    public void setP_md_time(String p_md_time) {
        this.p_md_time = p_md_time;
    }

    public String getP_md_min() {
        return p_md_min;
    }

    public void setP_md_min(String p_md_min) {
        this.p_md_min = p_md_min;
    }

    public String getP_md_max() {
        return p_md_max;
    }

    public void setP_md_max(String p_md_max) {
        this.p_md_max = p_md_max;
    }

    public String getP_pd_probability() {
        return p_pd_probability;
    }

    public void setP_pd_probability(String p_pd_probability) {
        this.p_pd_probability = p_pd_probability;
    }

    public String getP_pd_serious() {
        return p_pd_serious;
    }

    public void setP_pd_serious(String p_pd_serious) {
        this.p_pd_serious = p_pd_serious;
    }

    public String getP_pd_time() {
        return p_pd_time;
    }

    public void setP_pd_time(String p_pd_time) {
        this.p_pd_time = p_pd_time;
    }

    public String getP_pd_min() {
        return p_pd_min;
    }

    public void setP_pd_min(String p_pd_min) {
        this.p_pd_min = p_pd_min;
    }

    public String getP_pd_max() {
        return p_pd_max;
    }

    public void setP_pd_max(String p_pd_max) {
        this.p_pd_max = p_pd_max;
    }

    public String getP_ppqa_probability() {
        return p_ppqa_probability;
    }

    public void setP_ppqa_probability(String p_ppqa_probability) {
        this.p_ppqa_probability = p_ppqa_probability;
    }

    public String getP_ppqa_serious() {
        return p_ppqa_serious;
    }

    public void setP_ppqa_serious(String p_ppqa_serious) {
        this.p_ppqa_serious = p_ppqa_serious;
    }

    public String getP_ppqa_time() {
        return p_ppqa_time;
    }

    public void setP_ppqa_time(String p_ppqa_time) {
        this.p_ppqa_time = p_ppqa_time;
    }

    public String getP_ppqa_min() {
        return p_ppqa_min;
    }

    public void setP_ppqa_min(String p_ppqa_min) {
        this.p_ppqa_min = p_ppqa_min;
    }

    public String getP_ppaq_max() {
        return p_ppaq_max;
    }

    public void setP_ppaq_max(String p_ppaq_max) {
        this.p_ppaq_max = p_ppaq_max;
    }
}
