package com.chinaredstar.commonBiz.bean.constant;

/**
 * 费用类型
 * Created by bingcheng on 2015/9/17.
 */
public enum PropertyType {

    Property("property"),//物业费
    Parking("parking"),//停车费
    Water("water"),//水费
    Power("power");//电费

    private String type;

    private PropertyType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
