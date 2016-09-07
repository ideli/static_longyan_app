package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

/**
 * Created by lenovo on 2016/4/27.
 */
public class RedstarOrganizationMonth implements Identified {

    private int id;

    private  int organizationId;//  组织ID

    private  String organizationName;//  组织名称

    private  int year;//  年

    private  int month;//  月

    private  int inputCommunityAmount;//  小区录入数量

    private  int inputMemberAmount;//  会员录入数量


    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getInputCommunityAmount() {
        return inputCommunityAmount;
    }

    public void setInputCommunityAmount(int inputCommunityAmount) {
        this.inputCommunityAmount = inputCommunityAmount;
    }

    public int getInputMemberAmount() {
        return inputMemberAmount;
    }

    public void setInputMemberAmount(int inputMemberAmount) {
        this.inputMemberAmount = inputMemberAmount;
    }
}
