package com.chinaredstar.longyan.model;


import java.io.Serializable;


public class RedstarReportMallInputMonth implements Serializable {
    private static final long serialVersionUID = 4526092217520955571L;

    private Integer id;

    private Integer shoppingMallId;

    private String shoppingMallName;

    private Integer year;

    private Integer month;

    private Integer inputCommunityAmount;

    private Integer inputMemberAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShoppingMallId() {
        return shoppingMallId;
    }

    public void setShoppingMallId(Integer shoppingMallId) {
        this.shoppingMallId = shoppingMallId;
    }

    public String getShoppingMallName() {
        return shoppingMallName;
    }

    public void setShoppingMallName(String shoppingMallName) {
        this.shoppingMallName = shoppingMallName == null ? null : shoppingMallName.trim();
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

    public Integer getInputMemberAmount() {
        return inputMemberAmount;
    }

    public void setInputMemberAmount(Integer inputMemberAmount) {
        this.inputMemberAmount = inputMemberAmount;
    }
}