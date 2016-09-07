package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by niu on 2016/6/1.
 */
public class RedstarEmployeeDayInput implements Identified,DayInputInterface {

    private  int id;

    private Integer employeeId;//   员工ID

    private String employeeXingMing;//   员工姓名

    private Integer year;//   年

    private Integer month;//   月

    private Integer day;//   日

    private Integer inputCommunityAmount;//   录入小区数量

    private Integer inputMemberAmount;//   录入会员数量

    private Integer scoreAmount;//  总计

    private Integer scoreRank;//   总计排名


    public Integer getScoreAmount() {
        return scoreAmount;
    }

    public void setScoreAmount(Integer scoreAmount) {
        this.scoreAmount = scoreAmount;
    }

    public Integer getScoreRank() {
        return scoreRank;
    }

    public void setScoreRank(Integer scoreRank) {
        this.scoreRank = scoreRank;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getInputCommunityAmount() {
        return inputCommunityAmount;
    }

    public void setInputCommunityAmount(Integer inputCommunityAmount) {
        this.inputCommunityAmount = inputCommunityAmount;
    }

    public Integer getInputMemberAmount() {
        return inputMemberAmount;
    }

    public void setInputMemberAmount(Integer inputMemberAmount) {
        this.inputMemberAmount = inputMemberAmount;
    }

    @Override
    @JsonIgnore
    public String getDate() {
        return this.getYear().toString()+this.getMonth().toString()+this.getDay().toString();
    }
}
