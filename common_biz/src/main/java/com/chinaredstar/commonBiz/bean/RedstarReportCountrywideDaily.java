package com.chinaredstar.commonBiz.bean;

import com.xiwa.base.bean.Identified;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;

/**
 * Created by lenovo on 2016/5/16.
 */
public class RedstarReportCountrywideDaily implements Identified,DayInputInterface {

    private int id;

    private Integer year;// 年

    private  Integer month;// 月

    private Integer day;// 日

    private Date createDate;// 创建时间

    private  Integer newUserCount;//    新用户数量

    private  Integer activeUserCount;//  活跃用户数量

    private  Integer communityInputAmount;// 录入小区数量

    private  Integer memberInputAmount;//  会员录入数量


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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getNewUserCount() {
        return newUserCount;
    }

    public void setNewUserCount(Integer newUserCount) {
        this.newUserCount = newUserCount;
    }

    public Integer getActiveUserCount() {
        return activeUserCount;
    }

    public void setActiveUserCount(Integer activeUserCount) {
        this.activeUserCount = activeUserCount;
    }

    public Integer getCommunityInputAmount() {
        return communityInputAmount;
    }

    public void setCommunityInputAmount(Integer communityInputAmount) {
        this.communityInputAmount = communityInputAmount;
    }

    public Integer getMemberInputAmount() {
        return memberInputAmount;
    }

    public void setMemberInputAmount(Integer memberInputAmount) {
        this.memberInputAmount = memberInputAmount;
    }

    @Override
    @JsonIgnore
    public String getDate() {
        return this.getYear().toString()+this.getMonth().toString()+this.getDay().toString();
    }
}
