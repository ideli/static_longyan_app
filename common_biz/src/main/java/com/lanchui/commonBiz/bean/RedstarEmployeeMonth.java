package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

/**
 * Created by lenovo on 2016/4/26.
 */
//员工录入月报
public class RedstarEmployeeMonth implements Identified {

    private int id;

    private  Integer employeeId;//  员工ID

    private  String employeeXingMing;//  员工姓名

    private  Integer year;//  年

    private  Integer month;//  月

    private  Integer inputCommunityAmount;//  录入小区数量

    private Integer scoreRank;//    积分数

    private Integer scoreAmount;//  积分排名



    public Integer getInputMemberAmount() {
        return inputMemberAmount;
    }

    public void setInputMemberAmount(Integer inputMemberAmount) {
        this.inputMemberAmount = inputMemberAmount;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeXingMing() {
        return employeeXingMing;
    }

    public void setEmployeeXingMing(String employeeXingMing) {
        this.employeeXingMing = employeeXingMing;
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

    public Integer getInputCommunityAmount() {
        return inputCommunityAmount;
    }

    public void setInputCommunityAmount(Integer inputCommunityAmount) {
        this.inputCommunityAmount = inputCommunityAmount;
    }

    private  Integer inputMemberAmount;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getScoreRank() {
        return scoreRank;
    }

    public void setScoreRank(Integer scoreRank) {
        this.scoreRank = scoreRank;
    }

    public Integer getScoreAmount() {
        return scoreAmount;
    }

    public void setScoreAmount(Integer scoreAmount) {
        this.scoreAmount = scoreAmount;
    }
}
