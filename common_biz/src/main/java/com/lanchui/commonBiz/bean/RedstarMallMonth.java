package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

/**
 * Created by lenovo on 2016/4/27.
 */
public class RedstarMallMonth implements Identified {

    private int id;

    private  int shoppingMallId;//  商场ID

    private  String shoppingMallName;//  商场名称

    private  int year;//  年

    private  int month;// 月

    private  int inputCommunityAmount;//  录入小区数量

    private  int inputMemberAmount;//  录入会员数量

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShoppingMallId() {
        return shoppingMallId;
    }

    public void setShoppingMallId(int shoppingMallId) {
        this.shoppingMallId = shoppingMallId;
    }

    public String getShoppingMallName() {
        return shoppingMallName;
    }

    public void setShoppingMallName(String shoppingMallName) {
        this.shoppingMallName = shoppingMallName;
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
