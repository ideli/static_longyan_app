package com.chinaredstar.commonBiz.bean.constant;

/**
 * 上门允许选择的时间
 * Created by xiaobc on 15/11/30.
 */
public enum AllowChooceTime {

    NoLimit("no_limit"),//下单时间 没有限制
    OnlyTomorrow("only_tomorrow");//上门开始时间 只能选择明天


    private String type;

    AllowChooceTime(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
