package com.example.yuzelli.projectevaluation.entity;

import java.io.Serializable;

/**
 * Created by 51644 on 2017/5/5.
 */

public class Quality implements Serializable {


    private String q_density;
    private String q_eliminate;
    private String q_process;
    private int q_project_id;
    private String q_stage_process;
    private String q_time;
    private int quality_id;

    public Quality() {
    }

    public Quality(String q_density, String q_eliminate, String q_process, int q_project_id, String q_stage_process, String q_time, int quality_id) {
        this.q_density = q_density;
        this.q_eliminate = q_eliminate;
        this.q_process = q_process;
        this.q_project_id = q_project_id;
        this.q_stage_process = q_stage_process;
        this.q_time = q_time;
        this.quality_id = quality_id;
    }

    public String getQ_density() {
        return q_density;
    }

    public void setQ_density(String q_density) {
        this.q_density = q_density;
    }

    public String getQ_eliminate() {
        return q_eliminate;
    }

    public void setQ_eliminate(String q_eliminate) {
        this.q_eliminate = q_eliminate;
    }

    public String getQ_process() {
        return q_process;
    }

    public void setQ_process(String q_process) {
        this.q_process = q_process;
    }

    public int getQ_project_id() {
        return q_project_id;
    }

    public void setQ_project_id(int q_project_id) {
        this.q_project_id = q_project_id;
    }

    public String getQ_stage_process() {
        return q_stage_process;
    }

    public void setQ_stage_process(String q_stage_process) {
        this.q_stage_process = q_stage_process;
    }

    public String getQ_time() {
        return q_time;
    }

    public void setQ_time(String q_time) {
        this.q_time = q_time;
    }

    public int getQuality_id() {
        return quality_id;
    }

    public void setQuality_id(int quality_id) {
        this.quality_id = quality_id;
    }
}
