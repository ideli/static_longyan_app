package com.chinaredstar.longyan.model;


import java.util.Date;

/**
 * Created by mdc on 2016/8/3.
 */

public class RedstarLotterySetting extends BaseEntity {
    private Integer cdTime;

    private Integer totalTimes;

    private Integer workTimePercent;

    private Integer restTimePercent;

    private Date workStartTime;

    private Date workEndTime;

    public Integer getCdTime() {
        return cdTime;
    }

    public void setCdTime(Integer cdTime) {
        this.cdTime = cdTime;
    }

    public Integer getTotalTimes() {
        return totalTimes;
    }

    public void setTotalTimes(Integer totalTimes) {
        this.totalTimes = totalTimes;
    }

    public Integer getWorkTimePercent() {
        return workTimePercent;
    }

    public void setWorkTimePercent(Integer workTimePercent) {
        this.workTimePercent = workTimePercent;
    }

    public Integer getRestTimePercent() {
        return restTimePercent;
    }

    public void setRestTimePercent(Integer restTimePercent) {
        this.restTimePercent = restTimePercent;
    }

    public Date getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(Date workStartTime) {
        this.workStartTime = workStartTime;
    }

    public Date getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(Date workEndTime) {
        this.workEndTime = workEndTime;
    }
}
