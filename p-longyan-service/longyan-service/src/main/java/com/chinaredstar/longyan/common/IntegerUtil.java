package com.chinaredstar.longyan.common;


import com.alibaba.dubbo.common.utils.StringUtils;

/**
 * Created by lenovo on 2016/6/30.
 */
public class IntegerUtil {
    public static boolean isInteger(String var) {
        if (!StringUtils.isEmpty(var)){
            try{
                Integer.parseInt(var);
            } catch (Exception ex){
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
