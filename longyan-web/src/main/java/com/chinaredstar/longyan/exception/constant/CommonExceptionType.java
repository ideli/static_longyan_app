package com.chinaredstar.longyan.exception.constant;

/**
 * Created by wangj on 2015/6/1.
 */
public enum CommonExceptionType implements BasicExceptionType {

    Unknow("-9999", "未知异常"),
    NoSession("-401", "请重新登录"),
    ParameterError("1001", "缺少必填参数"),
    NoDataExistError("1002", "没有数据"),
    DeleteNoExist("1003", "需要删除的数据不存在");

    private String code;
    private String message;

    CommonExceptionType(String code, String message) {
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
