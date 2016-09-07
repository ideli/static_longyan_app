package com.greatbee.bean.constant;

/**
 * 付款主体
 * Created by wangj on 2015/6/3.
 */
public enum PayType {

    User("User"), Merchant("Merchant"), Scene("Scene");

    private String type;

    private PayType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
