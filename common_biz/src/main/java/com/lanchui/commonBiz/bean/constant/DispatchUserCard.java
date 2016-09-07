package com.lanchui.commonBiz.bean.constant;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by usagizhang on 15/12/17.
 */
public class DispatchUserCard implements Identified{
    private int id;
    private  String serialNumber;
    private Date createDate;
    private  int merchantId;
    private  String merchantName;
    private  int createMerchantUserId;
    private  String createMerchantUserXingMing;
    private  int communityId;
    private  String communityName;
    private  int createCommunityUserId;
    private  String createCommunityUserXingMing;
    private  int userId;
    private  String xingMing;
    private  String mobilePhone;
    private  Date validDate;
    private  int serviceNum;
    private  double serviceAmount;
    private  boolean valid;
    private  String name;
    private  int productId;
    private  String productName;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public int getCreateCommunityUserId() {
        return createCommunityUserId;
    }

    public void setCreateCommunityUserId(int createCommunityUserId) {
        this.createCommunityUserId = createCommunityUserId;
    }

    public String getCreateCommunityUserXingMing() {
        return createCommunityUserXingMing;
    }

    public void setCreateCommunityUserXingMing(String createCommunityUserXingMing) {
        this.createCommunityUserXingMing = createCommunityUserXingMing;
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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public int getServiceNum() {
        return serviceNum;
    }

    public void setServiceNum(int serviceNum) {
        this.serviceNum = serviceNum;
    }

    public double getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(double serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
