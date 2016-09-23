package com.chinaredstar.longyan.exception;

/**
 * Created by usagizhang on 16-7-16.
 */
public class NoSessionException extends BasicException {

    public static final String NO_SESSION_ERROR_CODE="-401";

    public NoSessionException() {
        super();
    }

    public NoSessionException(Throwable e) {
        super(e);
    }

    public NoSessionException(String message) {
        super(message);
    }

    public NoSessionException(String message, Throwable e) {
        super(message, e);
    }

    public NoSessionException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public NoSessionException(String errorCode, String message, Throwable e){
        super(message,e);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return NO_SESSION_ERROR_CODE;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
