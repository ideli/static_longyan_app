package com.lanchui.commonBiz.bean.constant;

import com.xiwa.base.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 群发短信队列 状态
 * Created by bingcheng on 2015/5/18.
 */
public enum MessageQueueStatus {

    Success("success","成功"),//成功
    Un_Send("un_send","未发送"),//未发送
    Failue("failue","失败");//失败


    private String type;
    private String text;

    MessageQueueStatus(String type, String text){
        this.type = type;
        this.text = text;
    }

    //根据key值拿到value
    public static String getValue(String key) {
        for (MessageQueueStatus type : MessageQueueStatus.values()) {
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
        for (MessageQueueStatus type : MessageQueueStatus.values()) {
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
