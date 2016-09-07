package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by wangj on 2015/7/21.
 */
public class DispatchCommunityGarden implements Identified {

    private int id;

    private Date createDate;//  创建时间

    private String createEmployeeId;//  创建员工ID

    private String createXingMing;//  创建人姓名

    private int merchantId;//  商户ID

    private String merchantName;//  商户姓名

    private int createMerchantUserId;// 创建商业用户ID

    private String createMerchantUserXingMing;//  创建商业用户姓名

    private int communityId;//  小区ID

    private String communityName;//  小区姓名

    private String createCommunityUserId;//  创建小区用户ID

    private String createCommunityUserXingMing;//  创建小区用户姓名

    private String garden;//

    private String remark;//  备注

    private boolean empty;//

    @Override
    public int getId() {
        return id;
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

    public String getCreateEmployeeId() {
        return createEmployeeId;
    }

    public void setCreateEmployeeId(String createEmployeeId) {
        this.createEmployeeId = createEmployeeId;
    }

    public String getCreateXingMing() {
        return createXingMing;
    }

    public void setCreateXingMing(String createXingMing) {
        this.createXingMing = createXingMing;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public int getCreateMerchantUserId() {
        return createMerchantUserId;
    }

    public void setCreateMerchantUserId(int createMerchantUserId) {
        this.createMerchantUserId = createMerchantUserId;
    }

    public String getCreateMerchantUserXingMing() {
        return createMerchantUserXingMing;
    }

    public void setCreateMerchantUserXingMing(String createMerchantUserXingMing) {
        this.createMerchantUserXingMing = createMerchantUserXingMing;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCreateCommunityUserId() {
        return createCommunityUserId;
    }

    public void setCreateCommunityUserId(String createCommunityUserId) {
        this.createCommunityUserId = createCommunityUserId;
    }

    public String getCreateCommunityUserXingMing() {
        return createCommunityUserXingMing;
    }

    public void setCreateCommunityUserXingMing(String createCommunityUserXingMing) {
        this.createCommunityUserXingMing = createCommunityUserXingMing;
    }

    public String getGarden() {
        return garden;
    }

    public void setGarden(String garden) {
        this.garden = garden;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
