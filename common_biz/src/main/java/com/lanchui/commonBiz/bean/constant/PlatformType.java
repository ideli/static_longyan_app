package com.lanchui.commonBiz.bean.constant;

/**
 * 平台设备类型
 * Created by zhangxuechao on 2015/11/20.
 */
public enum PlatformType {

    IPHONE("iPhone"),//iPhone
    IPAD("iPad"),//iPad
    Android("Android");//Android


    private String type;

    private PlatformType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
