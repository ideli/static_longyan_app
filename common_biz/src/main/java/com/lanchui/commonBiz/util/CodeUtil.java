package com.lanchui.commonBiz.util;

/**
 * Created by lenovo on 2016/4/25.
 */
public class CodeUtil {

    public    static String getVerifyCode(){
        return  String.valueOf(Math.random()).substring(2,8);
    }
}
