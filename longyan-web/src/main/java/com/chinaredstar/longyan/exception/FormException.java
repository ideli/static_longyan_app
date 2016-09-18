package com.chinaredstar.longyan.exception;

/**
 * Created by usagizhang on 16-7-16.
 */
public class FormException  extends BasicException {

    public FormException() {
        super();
    }

    public FormException(Throwable e) {
        super(e);
    }

    public FormException(String message) {
        super(message);
    }

    public FormException(String message, Throwable e) {
        super(message, e);
    }

    public FormException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public FormException(String errorCode,String message,Throwable e){
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
