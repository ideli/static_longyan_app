package com.chinaredstar.longyan.model;


import java.io.Serializable;
import java.util.Date;

public class RedstarReportCountrywideDaily implements Serializable {
    private static final long serialVersionUID = -1790008308566896851L;
    private Integer id;

    private Integer year;

    private Integer month;

    private Integer day;

    private Date createDate;

    private Integer newUserCount;

    private Integer activeUserCount;

    private Integer communityInputAmount;

    private Integer memberInputAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}