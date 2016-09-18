package com.chinaredstar.commonBiz.bean.constant;

import com.xiwa.base.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 出入库 状态字典
 * Created by bingcheng on 2015/5/18.
 */
public enum CargoOrderStatus {

    New("new","新建"),//新建
    Into("into","已入库"),//已入库
    Output("output","已出库"),//已出库
    Into_Fail("into_fail","入库失败"),//入库失败
    Output_Fail("output_fail","出库失败"),//出库失败
    Cancel("cancel","取消");//取消

    private String type;
    private String text;

    CargoOrderStatus(String type, String text){
        this.type = type;
        this.text = text;
    }

    //根据key值拿到value
    public static String getValue(String key) {
        for (CargoOrderStatus type : CargoOrderStatus.values()) {
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
        for (CargoOrderStatus type : CargoOrderStatus.values()) {
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
