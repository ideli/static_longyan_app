package com.chinaredstar.commonBiz.bean;

import com.xiwa.base.bean.Identified;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by niu on 2016/6/1.
 */
public class RedstarOrganizationDayInput implements Identified,DayInputInterface {


    private  int id;

    private Integer organizationId;//   组织ID

    private String organizationName;//   组织名称

    private Integer year;//   年

    private Integer month;//   月

    private Integer day;//   日

    private Integer inputCommunityAmount;//   小区录入数量

    private Integer inputMemberAmount;//   录入会员数量


    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
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
