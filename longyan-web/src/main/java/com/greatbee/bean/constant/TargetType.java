package com.greatbee.bean.constant;

/**
 * 订单更新，记录日志时，操作人的角色
 * Created by bingcheng on 2015/4/10.
 */
public enum TargetType {

    Employee("employee"), Worker("worker"), Merchant("merchant"),Community("community");

    private String type;

    private TargetType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
