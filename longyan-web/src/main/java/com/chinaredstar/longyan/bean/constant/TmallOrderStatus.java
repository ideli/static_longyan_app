package com.chinaredstar.longyan.bean.constant;

import com.xiwa.base.util.StringUtil;

/**
 * 天猫订单状态 值转换
 * Created by bingcheng on 2015/4/13.
 */
public enum TmallOrderStatus {
    Accept("3","预约"),
    Execute("4","上门"),
    Reject("10","预约失败"),
    Success("5","已完成"),
    Fail("11","失败");

    private String key;

    private String value;

    private TmallOrderStatus(String key,String value){
        this.key = key;
        this.value = value;
    }

    //根据key值拿到value
    public static String getValue(String key){
        for (TmallOrderStatus tmall:TmallOrderStatus.values()){
            if(StringUtil.equals(tmall.getKey(), key)){
                return tmall.getValue();
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
