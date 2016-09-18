package com.chinaredstar.commonBiz.bean.constant;

/**
 * 订单类型
 * Created by zhangxuechao on 2015/11/20.
 */
public enum OrderType {

    Product("product"),//商品类
    XIYI("dispatch_xiyi_order");//洗衣


    private String type;

    private OrderType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
