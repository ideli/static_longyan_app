package com.chinaredstar.longyan.exception;

import com.chinaredstar.longyan.exception.constant.BasicExceptionType;

/**
 * Created by usagizhang on 16-7-16.
 */
public class BusinessException extends BasicException {

    public BusinessException() {
        super();
    }

    public BusinessException(Throwable e) {
        super(e);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable e) {
        super(message, e);
    }

    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(BasicExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.errorCode = exceptionType.getCode();
    }

    public BusinessException(String errorCode, String message, Throwable e){
        super(message,e);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
