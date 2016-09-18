package com.chinaredstar.commonBiz.bean.constant;

import com.xiwa.zeus.trinity.bean.Community;

/**
 * target类别
 * Created by bingcheng on 2015/6/23.
 */
public enum TargetType {

    User("User"),//用户端
    Worker("Worker"),//工人端
    Merchant("Merchant"),//商户端
    Community("Community");

    private String type;

    private TargetType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
