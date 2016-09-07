package com.greatbee.bean.constant;

import com.xiwa.base.util.StringUtil;

/**
 * Created by wangj on 2015/9/10.
 */
public enum PayPeriodType {

    day("day", "按日-day"),
    month("month", "按月-month"),
    quarter("quarter", "按季度-quarter"),
    year("year", "按年-year");


    private String type;
    private String text;

    PayPeriodType(String type, String text) {
        this.type = type;
        this.text = text;
    }

    //根据key值拿到value
    public static String getValue(String key) {
        for (PayPeriodType type : PayPeriodType.values()) {
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
