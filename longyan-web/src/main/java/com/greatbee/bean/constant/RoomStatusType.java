package com.greatbee.bean.constant;

import com.xiwa.base.util.StringUtil;

/**
 * Created by wangj on 2015/9/10.
 */
public enum RoomStatusType {

    empty("empty", "空置房-empty"),
    owner("owner", "已入住-owner"),
    leased("leased", "已出租-leased"),
    sample_room("sample_room", "样板房-sample_room"),
    sold_out("sold_out", "已售房-sold_out"),
    hand_over("hand_over", "已交房-hand_over"),
    waiting_for_rent("waiting_for_rent", "待出租-waiting_for_rent"),
    special_room("special_room", "专用房-special_room"),
    court_seizure("court_seizure", "法院查封-court_seizure");


    private String type;
    private String text;

    RoomStatusType(String type, String text) {
        this.type = type;
        this.text = text;
    }

    //根据key值拿到value
    public static String getValue(String key) {
        for (RoomStatusType type : RoomStatusType.values()) {
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
