package com.lanchui.commonBiz.bean;

import com.xiwa.base.bean.Identified;

import java.util.Date;

/**
 * Created by wangj on 2015/5/4.
 */
public class DispatchMerchantAuthorized implements Identified {
    private int id;
    private String account;
    private String password;
    private int merchantId;
    private String xingMing;
    private Date createDate;
    private int createEmployeeId;
    private String createXingMing;
    private String roles;
    private boolean active;
    private boolean haveSetPassword;
    private boolean superAccount;
    private String managedCommunityIds;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getXingMing() {
        return xingMing;
    }

    public void setXingMing(String xingMing) {
        this.xingMing = xingMing;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getCreateEmployeeId() {
        return createEmployeeId;
    }

    public void setCreateEmployeeId(int createEmployeeId) {
        this.createEmployeeId = createEmployeeId;
    }

    public String getCreateXingMing() {
        return createXingMing;
    }

    public void setCreateXingMing(String createXingMing) {
        this.createXingMing = createXingMing;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isHaveSetPassword() {
        return haveSetPassword;
    }

    public void setHaveSetPassword(boolean haveSetPassword) {
        this.haveSetPassword = haveSetPassword;
    }

    public boolean isSuperAccount() {
        return superAccount;
    }

    public void setSuperAccount(boolean superAccount) {
        this.superAccount = superAccount;
    }

    public String getManagedCommunityIds() {
        return managedCommunityIds;
    }

    public void setManagedCommunityIds(String managedCommunityIds) {
        this.managedCommunityIds = managedCommunityIds;
    }
}