package com.greatbee.bean.constant;

/**
 * task执行结果
 * Created by wangj on 2015/6/19.
 */
public enum TaskResult {

    SUCCESS("SUCCESS"), FAILURE("FAILURE");

    private String result;

    TaskResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
