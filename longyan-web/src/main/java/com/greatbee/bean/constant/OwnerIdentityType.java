package com.greatbee.bean.constant;

import com.xiwa.base.util.StringUtil;

/**
 * Created by wangj on 2015/9/10.
 */
public enum OwnerIdentityType {

    owner("owner", "业主-owner"),
    tenant("tenant", "租户-tenant"),
    property("property", "物业-property"),
    thirdParty("thirdParty", "第三方-thirdParty");


    private String type;
    private String text;

    OwnerIdentityType(String type, String text) {
        this.type = type;
        this.text = text;
    }

    //根据key值拿到value
    public static String getValue(String key) {
        for (OwnerIdentityType type : OwnerIdentityType.values()) {
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
