package com.lanchui.commonBiz.bean.constant;

/**
 * 销售状态
 * Created by zhangxuechao on 2015/11/20.
 */
public enum ProductStatusType {

    Online("Online"),//上架
    Offline("Offline");//下架


    private String type;

    private ProductStatusType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
