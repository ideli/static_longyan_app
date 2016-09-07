package com.lanchui.commonBiz.bean.constant;

import com.xiwa.base.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 保修类型
 * Created by bingcheng on 2015/5/18.
 */
public enum RepairType {

    Repair_Public("68","公共保修"),//公共保修
    Repair_Person("67","个人保修");//个人保修


    private String type;
    private String text;

    RepairType(String type, String text){
        this.type = type;
        this.text = text;
    }

    //根据key值拿到value
    public static String getValue(String key) {
        for (RepairType type : RepairType.values()) {
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
        for (RepairType type : RepairType.values()) {
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
