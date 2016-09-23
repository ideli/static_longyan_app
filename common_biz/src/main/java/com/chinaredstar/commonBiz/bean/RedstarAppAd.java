package com.chinaredstar.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;


/**
 * Created by lenovo on 2016/7/13.
 */
public class RedstarAppAd implements Identified {

    private int id;

    private Date createDate;//  创建时间

    private Integer createEmployeeId;//  创建员工ID

    private String createXingMing;//  创建人姓名

    private String android720p;//

    private String android1280p;//

    private String ios47;//

    private String ios55;//

    private String ios40;//

    private String ios35;//

    private Integer activity;   //是否激活  1：激活，0：未激活


    @Override
    public int getId() {
        return 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getCreateEmployeeId() {
        return createEmployeeId;
    }

    public void setCreateEmployeeId(Integer createEmployeeId) {
        this.createEmployeeId = createEmployeeId;
    }

    public String getCreateXingMing() {
        return createXingMing;
    }

    public void setCreateXingMing(String createXingMing) {
        this.createXingMing = createXingMing;
    }

    public String getAndroid720p() {
        return android720p;
    }

    public void setAndroid720p(String android720p) {
        this.android720p = android720p;
    }

    public String getAndroid1280p() {
        return android1280p;
    }

    public void setAndroid1280p(String android1280p) {
        this.android1280p = android1280p;
    }

    public String getIos47() {
        return ios47;
    }

    public void setIos47(String ios47) {
        this.ios47 = ios47;
    }

    public String getIos55() {
        return ios55;
    }

    public void setIos55(String ios55) {
        this.ios55 = ios55;
    }

    public String getIos40() {
        return ios40;
    }

    public void setIos40(String ios40) {
        this.ios40 = ios40;
    }

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }

    public String getIos35() {
        return ios35;
    }

    public void setIos35(String ios35) {
        this.ios35 = ios35;
    }



}
