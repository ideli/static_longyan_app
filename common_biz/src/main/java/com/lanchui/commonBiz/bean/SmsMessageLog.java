package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by bingcheng on 2015/3/24.
 */
public class SmsMessageLog implements Identified {
    //实现Identified并且绑定到表xiwa_message_sms_log

    private  int id;

    private  String phone;//    电话

    private  String requestUrl;//   请求地址

    private  Date createDate;// 创建时间 使用 new Date() 生成

    private  String source;//   来源

    private  String message;//  短信内容

    private  String status;//   发送状态 成功=success，失败=failure

    private  String serialNumber;// 序列号 使用 RandomGUIDUtil.getRawGUID().replace("-",""); 生成

    private  String orderId;//  订单ID

    private  String orderSerialNumber;//    订单序列号

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getOrderId() { return orderId; }

    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getOrderSerialNumber() { return orderSerialNumber; }

    public void setOrderSerialNumber(String orderSerialNumber) { this.orderSerialNumber = orderSerialNumber; }
}
