package com.chinaredstar.longyan.exception;

/**
 * Created by usagizhang on 16-7-16.
 */
public class BasicException extends RuntimeException {

    protected static final long serialVersionUID = 1L;

    String errorCode = "200";

    public BasicException() {
        super();
    }

    public BasicException(Throwable e) {
        super(e);
    }

    public BasicException(String message) {
        super(message);
    }

    public BasicException(String message, Throwable e) {
        super(message, e);
    }

    public BasicException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BasicException(String errorCode, String message, Throwable e){
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
