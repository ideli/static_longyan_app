package com.chinaredstar.commonBiz.bean;

import com.xiwa.base.bean.Identified;

/**
 * Created by mdc on 2016/7/13.
 */
public class SecurityAuthorized implements Identified {

    private int id;

    private String password;//    密码

    private Integer belongId;//

    private Integer objectId;//

    private String objectIdentified;//    对象标识符

    private String objectDesc;//

    private String account;//   账户

    private String securityQuestion;//    安全问题；找回密码问题；安全提问

    private String securityAnswer;//    安全问题答案

    private String activeCode;//    活跃代码

    private boolean active; //    是否活跃

    private String roles;//    角色

    private boolean haveSetPassword;    //是否有设置密码


    public boolean isHaveSetPassword() {
        return haveSetPassword;
    }

    public void setHaveSetPassword(boolean haveSetPassword) {
        this.haveSetPassword = haveSetPassword;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getBelongId() {
        return belongId;
    }

    public void setBelongId(Integer belongId) {
        this.belongId = belongId;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getObjectIdentified() {
        return objectIdentified;
    }

    public void setObjectIdentified(String objectIdentified) {
        this.objectIdentified = objectIdentified;
    }

    public String getObjectDesc() {
        return objectDesc;
    }

    public void setObjectDesc(String objectDesc) {
        this.objectDesc = objectDesc;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}

