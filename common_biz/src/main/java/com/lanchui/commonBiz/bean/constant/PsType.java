package com.lanchui.commonBiz.bean.constant;

/**
 * 配送方式
 * Created by zhangxuechao on 2015/12/07.
 */
public enum PsType {

    Self("self"),//自提
    OnBoard("on_board");//上门


    private String type;

    private PsType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
