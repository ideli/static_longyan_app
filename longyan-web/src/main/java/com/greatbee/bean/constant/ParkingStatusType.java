package com.greatbee.bean.constant;

import com.xiwa.base.util.StringUtil;

/**
 * Created by wangj on 2015/9/16.
 */
public enum ParkingStatusType {

    empty("empty", "空置-empty"),
    renter("renter", "出租中-renter"),
    sold("sold", "已卖-sold");


    private String type;
    private String text;

    ParkingStatusType(String type, String text) {
        this.type = type;
        this.text = text;
    }

    //根据key值拿到value
    public static String getValue(String key) {
        for (ParkingStatusType type : ParkingStatusType.values()) {
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
