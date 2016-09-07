package com.lanchui.commonBiz.bean.constant;

/**
 * 社区物业费 付款状态
 * Created by bingcheng on 2015/9/17.
 */
public enum PaymentStatus {

    Unpay("Unpay"),//未付款
    Payed("Payed");//已付清

    private String type;

    private PaymentStatus(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
