package com.greatbee.bean.constant;

import com.xiwa.base.util.StringUtil;

/**
 * Created by wangj on 2015/6/1.
 */
public enum SourceType {

    BD("BD", "BD商务拓展"),
    TM("TM", "天猫"),
    Merchant("Merchant", "商户"),
    User("User", "用户"),
    Community("Community", "社区");

    private String type;
    private String text;

    SourceType(String type, String text) {
        this.type = type;
        this.text = text;
    }

    //根据key值拿到value
    public static String getValue(String key) {
        for (SourceType type : SourceType.values()) {
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
