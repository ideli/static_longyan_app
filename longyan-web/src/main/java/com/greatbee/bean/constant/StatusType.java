package com.greatbee.bean.constant;

import com.xiwa.base.util.StringUtil;

/**
 * Created by wangj on 2015/6/1.
 */
public enum  StatusType {

    New("New","新建"),
    Waiting_Distribute("Waiting_Distribute","等待派单"),
    Waiting_RE_Distribute("Waiting_RE_Distribute","等待重新派单"),
    Waiting_Worker_Confirm("Waiting_Worker_Confirm","等待工人确定"),
    Waiting_Worker_On_Board("Waiting_Worker_On_Board","等待工人上门"),
    Waiting_Worker_Finish("Waiting_Worker_Finish","等待工人完工"),
    Waiting_Visit("Waiting_Visit","等待回访"),
    Waiting_Settlement("Waiting_Settlement","等待结算"),
    Refuse("Refuse","退单"),
    Finish("Finish","已完成"),
    Appointment("Appointment","预约中"),
    In_Settlement("In_Settlement","结算中"),
    User_Cancel("User_Cancel","用户取消"),
    Challenging("Challenging","抢单中"),
    Waiting_User_Payment("Waiting_User_Payment","等待用户付款");

    private String type;
    private String text;

    StatusType(String type, String text) {
        this.type = type;
        this.text = text;
    }

    //根据key值拿到value
    public static String getValue(String key) {
        for (StatusType type : StatusType.values()) {
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
