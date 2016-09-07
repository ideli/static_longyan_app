package com.chinaredstar.longyan.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by mdc on 2016/8/3.
 */
@Entity
@Table(name = "xiwa_redstar_lottery_setting")
public class RedstarLotterySetting extends BaseEntity {
    private Integer cdTime;

    private Integer totalTimes;

    private Integer workTimePercent;

    private Integer restTimePercent;

    private String workStartTime;

    private String workEndTime;


    public double getPercent() {
        DateTime now = DateTime.now();
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:SS");
        DateTime workStartTime = DateTime.parse(now.toString("yyyy-MM-dd") + " " + this.workStartTime + ":00", format);
        DateTime workEndTime = DateTime.parse(now.toString("yyyy-MM-dd") + " " + this.workEndTime + ":00", format);
        if (workStartTime.isBefore(now) && workEndTime.isAfter(now)) {//workTime
            return this.workTimePercent;
        } else {
            return this.restTimePercent;
        }
    }

//    public static void main(String[] args) {
//        Random rand = new Random();
//        int num = rand.nextInt(3);
//        System.out.println(num);
//    }

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

    public String getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(String workStartTime) {
        this.workStartTime = workStartTime;
    }

    public String getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(String workEndTime) {
        this.workEndTime = workEndTime;
    }
}
