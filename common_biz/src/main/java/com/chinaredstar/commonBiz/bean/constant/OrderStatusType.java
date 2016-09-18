package com.chinaredstar.commonBiz.bean.constant;

/**
 * Created by wangj on 2015/9/17.
 */
public enum OrderStatusType {
    New("New"),//新建
    Appointment("Appointment"),//预约中
    WaitingWorkerOnBoard("Waiting_Worker_On_Board"),//等待上门
    InProgress("InProgress"),
    Waiting_User_Payment("Waiting_User_Payment"),//等待用户付款
    Waiting_Delivery("Waiting_Delivery"),//等待用户确认收货
    Waiting_Evaluation("Waiting_Evaluation"),//等待用户评价
    Finish("Finish"),//已完成
    Complete("Complete"),
    All("All"),//查询全部的订单
    UserCancel("User_Cancel"),
    Deleted("Deleted");//删除

    private String type;

    OrderStatusType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
