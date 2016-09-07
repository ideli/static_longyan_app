package com.greatbee.bean.constant;

import com.xiwa.base.util.StringUtil;

/**
 * Created by wangj on 2015/10/13.
 */
public enum RoomAreaType {

    House("House", "别墅"),
    Store("Store", "商铺"),
    Apartment("Apartment", "公寓");


    private String type;
    private String text;

    RoomAreaType(String type, String text) {
        this.type = type;
        this.text = text;
    }

    //根据key值拿到value
    public static String getValue(String key) {
        for (RoomAreaType type : RoomAreaType.values()) {
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
