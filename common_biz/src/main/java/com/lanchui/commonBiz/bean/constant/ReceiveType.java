package com.lanchui.commonBiz.bean.constant;

import com.xiwa.base.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠券  领取方式
 * Created by bingcheng on 2015/5/18.
 */
public enum ReceiveType {

    Provide("provide","发放形式"),//发放形式
    User_Register("user_register","用户注册自动领取");//用户注册自动领取


    private String type;
    private String text;

    ReceiveType(String type, String text){
        this.type = type;
        this.text = text;
    }

    //根据key值拿到value
    public static String getValue(String key) {
        for (ReceiveType type : ReceiveType.values()) {
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
        for (ReceiveType type : ReceiveType.values()) {
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
