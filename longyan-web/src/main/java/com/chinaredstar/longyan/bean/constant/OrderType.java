package com.chinaredstar.longyan.bean.constant;

import com.xiwa.base.util.StringUtil;

/**
 * 订单类型
 *
 * @author wangj
 */
public enum OrderType {
    dispatch_floor_order("dispatch_floor_order", "地板订单"),
    dispatch_wallpaper_order("dispatch_wallpaper_order", "墙纸订单"),
    dispatch_bathroom_order("dispatch_bathroom_order", "卫浴订单"),
    dispatch_dd_order("dispatch_dd_order", "吊顶订单"),
    dispatch_yuba_order("dispatch_yuba_order", "浴霸订单"),
    dispatch_dj_order("dispatch_dj_order", "灯具订单"),
    dispatch_repair_order("dispatch_repair_order", "维修订单"),
    dispatch_measure_order("dispatch_measure_order", "测量订单"),
    dispatch_woodendoors_order("dispatch_woodendoors_order", "木门订单"),
    dispatch_property_repair_order("dispatch_property_repair_order", "物业报修"),
    dispatch_logistics_order("dispatch_logistics_order", "物流订单");

    private String type;

    private String text;


    private OrderType(String type, String text) {
        this.type = type;
        this.text = text;
    }

    //根据key值拿到value
    public static String getValue(String key) {
        for (OrderType type : OrderType.values()) {
            if (StringUtil.equals(type.getType(), key)) {
                return type.getText();
            }
        }
        return key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
