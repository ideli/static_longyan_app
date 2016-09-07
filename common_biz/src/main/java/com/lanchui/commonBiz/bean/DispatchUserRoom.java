package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by wangj on 2015/9/7.
 */
public class DispatchUserRoom implements Identified {

    private int id;
    private int userId;
    private String xingMing;
    private String phone;
    private int roomId;
    private int merchantId;
    private String merchantName;
    private int createMerchantUserId;
    private String createMerchantUserName;
    private int communityId;
    private String communityName;
    private int createCommunityUserId;
    private String createCommunityUserName;
    private Date createDate;
    private String ownerIdentity;

    private String verify;
    private boolean loginMark;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getXingMing() {
        return xingMing;
    }

    public void setXingMing(String xingMing) {
        this.xingMing = xingMing;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getCreateMerchantUserId() {
        return createMerchantUserId;
    }

    public void setCreateMerchantUserId(int createMerchantUserId) {
        this.createMerchantUserId = createMerchantUserId;
    }

    public String getCreateMerchantUserName() {
        return createMerchantUserName;
    }

    public void setCreateMerchantUserName(String createMerchantUserName) {
        this.createMerchantUserName = createMerchantUserName;
    }

    public int getCreateCommunityUserId() {
        return createCommunityUserId;
    }

    public void setCreateCommunityUserId(int createCommunityUserId) {
        this.createCommunityUserId = createCommunityUserId;
    }

    public String getCreateCommunityUserName() {
        return createCommunityUserName;
    }

    public void setCreateCommunityUserName(String createCommunityUserName) {
        this.createCommunityUserName = createCommunityUserName;
    }

    public String getOwnerIdentity() {
        return ownerIdentity;
    }

    public void setOwnerIdentity(String ownerIdentity) {
        this.ownerIdentity = ownerIdentity;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public boolean isLoginMark() {
        return loginMark;
    }

    public void setLoginMark(boolean loginMark) {
        this.loginMark = loginMark;
    }
}


