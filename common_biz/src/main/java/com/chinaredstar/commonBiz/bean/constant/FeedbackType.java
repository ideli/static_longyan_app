package com.chinaredstar.commonBiz.bean.constant;

/**
 * 意见反馈类型
 * Created by zhangxuechao on 2015/11/20.
 */
public enum FeedbackType {

    Mistake("mistake"),//有错误
    Suggestion("suggestion");//建议


    private String type;

    private FeedbackType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
