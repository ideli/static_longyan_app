package com.chinaredstar.commonBiz.bean.constant;

import com.xiwa.base.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠券  使用场景类型
 * Created by bingcheng on 2015/5/18.
 */
public enum UseSceneType {

    All("all","任意"),//任意
    Product("product","限定产品");//限定产品


    private String type;
    private String text;

    UseSceneType(String type, String text){
        this.type = type;
        this.text = text;
    }

    //根据key值拿到value
    public static String getValue(String key) {
        for (UseSceneType type : UseSceneType.values()) {
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
        for (UseSceneType type : UseSceneType.values()) {
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
