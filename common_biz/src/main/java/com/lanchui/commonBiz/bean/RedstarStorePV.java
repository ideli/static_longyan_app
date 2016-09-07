package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by LeiYun on 2016/6/29.
 */
public class RedstarStorePV implements Identified {

    private int id;

    private String dataSK;//    事件名称

    private Date createDate;//  创建时间

    private String action;//    事件类型



    public int getId() {
        return id;
    }

    public String getDataSK() {
        return dataSK;
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


    public void setAction(String action) {
        this.action = action;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
