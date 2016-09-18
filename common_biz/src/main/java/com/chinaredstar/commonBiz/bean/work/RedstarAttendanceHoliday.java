package com.chinaredstar.commonBiz.bean.work;

import com.xiwa.base.bean.Identified;

/**
 * Created by lenovo on 2016/7/26.
 */
public class RedstarAttendanceHoliday implements Identified {


    private  int id;
    private Integer  year;
    private Integer  month;
    private Integer  day;
    private  String remark;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
