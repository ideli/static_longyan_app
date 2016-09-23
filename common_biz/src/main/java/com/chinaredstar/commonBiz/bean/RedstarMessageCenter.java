package com.chinaredstar.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by StevenDong on 2016/9/19 0019.
 */
public class RedstarMessageCenter implements Identified  {

    private int id;

    private  int recipientID;   //消息接收者ID

    private  String recipientName;  //消息接收者姓名

    private  String messageTitle;   //消息标题

    private  String messageType;   //消息类型

    private  String messageSource; //消息来源

    private  String messageContent; //消息内容

    private  int readFlg;   //消息读取状态

    private Date createDate;  //消息创建时间

    private Date readDate;  //消息读取时间

    private String router; // 需要跳转到的页面

    @Override
    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRouter() {
        return router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    public Integer getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(Integer recipientID) {
        this.recipientID = recipientID;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(String messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Integer getReadFlg() {
        return readFlg;
    }

    public void setReadFlg(Integer readFlg) {
        this.readFlg = readFlg;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }
}
