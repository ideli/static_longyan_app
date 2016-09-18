package com.chinaredstar.longyan.bean.constant;

import com.xiwa.base.util.StringUtil;

/**
 * 商户结算状态
 * Created by wangj on 2015/6/1.
 */
public enum MerchantStatus {

    Merchant_Waiting_Settlement("Merchant_Waiting_Settlement", "商户待结算"),
    Merchant_Already_Settlement("Merchant_Already_Settlement", "商户已结算"),
    Merchant_In_Settlement("Merchant_In_Settlement", "商户结算中");

    private String type;
    private String text;

    MerchantStatus(String type, String text) {
        this.type = type;
        this.text = text;
    }

    //根据key值拿到value
    public static String getValue(String key) {
        for (MerchantStatus type : MerchantStatus.values()) {
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
