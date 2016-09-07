package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by LeiYun on 2016/6/29.
 */
public class RedstarStoreUV implements Identified {
    private int id;

    private  String dataSK;//   事件名称

    private int userId;//   用户Id

    private String userObject; //   用户来源

    private String action;//   事件类型

    private Date createDate;//  创建时间

    public int getId() {
        return id;
    }

    public String getDataSK() {
        return dataSK;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserObject() {
        return userObject;
    }

    public String getAction() {
        return action;
    }

    public Date getCreateDate() {
        return createDate;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setDataSK(String dataSK) {
        this.dataSK = dataSK;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserObject(String userObject) {
        this.userObject = userObject;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
