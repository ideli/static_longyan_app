package com.chinaredstar.commonBiz.bean.constant;

/**
 * Created by xiaobc on 15/11/30.
 */
public enum DeliveryDateType {

    SameDay("same_day"),//当日配送
    NextDay("next_day");//次日配送


    private String type;

    DeliveryDateType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}