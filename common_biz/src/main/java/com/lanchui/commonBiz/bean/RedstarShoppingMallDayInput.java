package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by niu on 2016/6/1.
 */
public class RedstarShoppingMallDayInput implements Identified,DayInputInterface {


    private  int id;

    private  Integer shoppingMallId;//  商场ID

    private  String shoppingMallName;//    商场名称

    private Integer year;//    年

    private Integer month;//    月

    private Integer day;//    日

    private Integer inputCommunityAmount;//    录入小区数量

    private Integer inputMemberAmount;//    录入员工数量

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        this.shoppingMallName = shoppingMallName;
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
