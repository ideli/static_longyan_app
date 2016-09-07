package com.greatbee.exception.constant;

import com.xiwa.base.util.StringUtil;

/**
 * Created by wangj on 2015/6/1.
 */
public enum CommunityExceptionType implements BasicExceptionType {
    DeleteInUse("401102", "该栋已被占用，不可删除"),

    CreateSameNameCommunity("111111", "该名称小区已存在");

    private String code;
    private String message;

    CommunityExceptionType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
