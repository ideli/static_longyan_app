package com.chinaredstar.commonBiz.bean.constant;

import com.xiwa.base.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠券  状态
 * Created by bingcheng on 2015/5/18.
 */
public enum CouponStatus {

    Un_Receive("un_receive","未领取"),//未领取
    Un_Use("un_use","未使用"),//未使用
    Use("use","已使用"),//已使用
    Overdue("overdue","已过期");//已过期


    private String type;
    private String text;

    CouponStatus(String type, String text){
        this.type = type;
        this.text = text;
    }

    //根据key值拿到value
    public static String getValue(String key) {
        for (CouponStatus type : CouponStatus.values()) {
            if (StringUtil.equals(type.getType(), key)) {
                return type.getText();
            }
        }
        return key;
    }

    /**
     * 获取所有text
     * @return
     */
    public static List<String> getValues(){
        ArrayList<String> list = new ArrayList<String>();
        for (CouponStatus type : CouponStatus.values()) {
            list.add(type.getText());
        }
        return list;
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
