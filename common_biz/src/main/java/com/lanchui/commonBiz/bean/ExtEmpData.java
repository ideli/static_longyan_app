package com.lanchui.commonBiz.bean;

import java.util.Date;

/**
 * Created by lenovo on 2016/4/27.
 */
public class ExtEmpData  {

    private int id;

    private  String xingMing;


    private  int inputMemberAmount;
    private  int inputCommunityAmount;


    private Integer inputMemberAmountRank;

    //private Integer inputCommunityAmountRank;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getXingMing() {
        return xingMing;
    }

    public void setXingMing(String xingMing) {
        this.xingMing = xingMing;
    }

    public int getInputMemberAmount() {
        return inputMemberAmount;
    }

    public void setInputMemberAmount(int inputMemberAmount) {
        this.inputMemberAmount = inputMemberAmount;
    }

    public int getInputCommunityAmount() {
        return inputCommunityAmount;
    }

    public void setInputCommunityAmount(int inputCommunityAmount) {
        this.inputCommunityAmount = inputCommunityAmount;
    }

    public Integer getInputMemberAmountRank() {
        return inputMemberAmountRank;
    }

    public void setInputMemberAmountRank(Integer inputMemberAmountRank) {
        this.inputMemberAmountRank = inputMemberAmountRank;
    }
}
